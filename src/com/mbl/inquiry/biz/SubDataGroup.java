package com.mbl.inquiry.biz;

import com.ibbl.common.dao.EibsDL;
import com.ibbl.common.model.deposit.CustomerPO;
import com.ibbl.common.model.deposit.GrpMemberPO;
import com.ibbl.common.model.deposit.IndividualCustomerPO;
import com.ibbl.data.model.GError;
import com.ibbl.data.model.JointCustomerData;
import com.ibbl.data.model.SError;
import com.ibbl.data.model.SubjectData;
import com.ibbl.exception.PersistentLayerException;
import com.ibbl.inquiry.action.bean.CustomerDataBean;
import com.ibbl.inquiry.util.ActionResult;
import com.ibbl.util.CIBDictionary;
import com.ibbl.util.FinalData;
import com.ibbl.util.RectifyUtil;
import com.ibbl.util.enums.CustType;
import com.ibbl.util.enums.DDD;
import com.ibbl.util.enums.ErrorCat;
import com.ibbl.util.enums.ErrorDef;
import ibbl.deposit.common.util.codeformat.CustNoFactory;
import ibbl.deposit.common.util.codeformat.ICustNo;
import ibbl.remote.connection.hibernate.SchemaFactory;
import ibbl.remote.tx.TxController;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.validator.GenericValidator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p>
 * Original author: Ayatullah Khomeni
 * <br/> Date: 28/06/2016
 * <br/> Last modification by: ayat $
 * <br/> Last modification on 28/06/2016: 11:57 AM
 * <br/> Current revision: : 1.1.1.1
 * </p>
 * Revision History: The method List<CustomerDataBean> getDataGroup of this class is use to
 * get Customer data list on creating IR
 * ------------------
 */
public class SubDataGroup {
    public static String PAD_STR_S = " ";
    public static int PH_NO_LENGTH = 40;
    public static int TIN_LENGTH = 12;
    public static int OTHER_ID_LENGTH = 20;
    public static int NAME_LENGTH = 70;
    public static int ADDRESS_LENGTH = 100;
    public static int COUNTRY_NAME_LENGTH = 33;
    public static int DIST_NAME_LENGTH = 18;
    public static int PC_CODE_LENGTH = 4;

    public List<SubjectData> dataList = new ArrayList<>();
    public List<JointCustomerData> jointCustList = new ArrayList<>();
    public SubjectData data;
    public List<GError> gErrorList = new ArrayList<>();
    public List<SError> sErrorList = new ArrayList<>();
    public GError gError;


    public static void main(String[] args) {
        try {
            List<CustomerDataBean> data = (new SubDataGroup()).getCustomerDataBeanGroup("213", "2130400022317");
            System.out.println(data.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ICustNo custNoObj = CustNoFactory.getCustNoObject();
    private CustType custType = CustType.INDIVIDUAL;
    private boolean groupError = false;
    private String custId = "";
    private String brCode = "";


    @SuppressWarnings("unchecked")
    public List<CustomerDataBean> getCustomerDataBeanGroup(String brCodeM, String masterID) {
        List<SubjectData> dataList = getSubjectDataGroup(brCodeM, masterID);
        return com.ibbl.cib.util.CIBUtil.makeCustomerBeanList(dataList);
    }
    /**
     * Is use to get Customer data list on creating IR
     * @param brCodeM Branch Code
     * @param masterID Master Customer ID
     * @return CustomerDataBean
     */
    @SuppressWarnings("unchecked")
    public List<SubjectData> getSubjectDataGroup(String brCodeM, String masterID) {
        custId = masterID;
        brCode = brCodeM;

        SchemaFactory schema;
        try {
            schema = SchemaFactory.init(brCode);
        } catch (Exception e) {
            return null;
        }

        TxController txCO = TxController.createInstance(schema);
        int txID = 0;
        try {
            txID = txCO.initPersistence();
        } catch (PersistentLayerException e) {
            e.printStackTrace();
            return null;
        }

        try {
            EibsDL eibsDL = new EibsDL(txID);

            CustomerPO customer = eibsDL.getCustomerByCustId(custId);

            custId = customer.getCustID();
            custType = CustType.get(custNoObj.getCustomerType(custId));
            if (custType == null) return null;

            String custBrCode = customer.getBranchCode();
            if (!custBrCode.equals(brCode)) {
                gError = new GError(ErrorCat.SUSPICIOUS, ErrorDef.MISS_ENTRY, custId, "Customer Br code not matched.");
                gErrorList.add(gError);
            }
            data = new SubjectData();
            data.setGroupSerial(0);

            try {
                transferData_GENERAL(customer); // First General Data Transfer

                SubjectData cloneMaster;
                List<SubjectData> childrenList = new ArrayList<>();
                List<SubjectData> guarantorList;
                if (custType == CustType.INDIVIDUAL) {
                    IndividualCustomerPO indCust = eibsDL.getIndividualCustomerByCustId(custId);
                    if (indCust != null) {
                        boolean gTransferred2 = transferData_GENERAL(indCust);
                        if (!gTransferred2) {
                            data.setError(FinalData.EXIM_DATA_ERROR);
                            data.setErrorCode(FinalData.ERROR_CODE_IMPORT_FAILED_GD);
                        }
                        transferData_P(indCust);
                    } else {
                        data.setError(FinalData.EXIM_DATA_ERROR);
                        gErrorList.add(new GError(ErrorCat.MAJOR, ErrorDef.NULL, custId, "No Individual Customer Info Found")); // TODO
                        data.setErrorCode(FinalData.ERROR_CODE_IMPORT_FAILED_IDF);
                    }
                    cloneMaster = (SubjectData) BeanUtils.cloneBean(data);
                } else {
                    transferData_CI(customer);
                    cloneMaster = (SubjectData) BeanUtils.cloneBean(data);
                    childrenList = getGrpCustSubjectData(custId, cloneMaster, eibsDL);
                    if (CollectionUtils.isEmpty(childrenList)) {
                        cloneMaster.setError(FinalData.EXIM_DATA_ERROR);
                        gErrorList.add(new GError(ErrorCat.MAJOR, ErrorDef.INVALID_GRP_MEM, custId, "No Group Member Customer Info Found")); // TODO
                        cloneMaster.setErrorCode(FinalData.ERROR_CODE_NO_GRP_MEMBER_FOUND);
                    }
                }

                guarantorList = getGuarantorSubjectData(custId, cloneMaster, eibsDL); // TODO... Open for all type customer later
                if (!CollectionUtils.isEmpty(guarantorList)) {
                    childrenList.addAll(guarantorList);
                }


                /**
                 * Borrower, Co-Borrower Setup
                 */
                if (cloneMaster.getCustType() == CustType.JOINT.CODE) {
                    if (!CollectionUtils.isEmpty(childrenList)) {
                        try {
                            SubjectData borrower = childrenList.stream()
                                    .filter(b -> b.getRelation().equals(FinalData.REL_BORROWER))
                                    .findFirst()
                                    .get();
                            String borrowerID = borrower.getCustId();
                            childrenList.stream().forEach(sd -> {
                                String cbCustId = sd.getCustId();
                                sd.setBorrCustId(borrowerID.equals(cbCustId) ? null : borrowerID);

                                JointCustomerData jcd = new JointCustomerData();
                                String cbCustIdG = sd.getGrpCustId();
                                jcd.setOid(cbCustId + (GenericValidator.isBlankOrNull(cbCustIdG) ? "" : ("_" + cbCustIdG)));
                                jcd.setBrCode(brCode);
                                jcd.setCustId(cbCustId);
                                jcd.setGrpCustId(cbCustIdG);
                                jcd.setBorrowerId(borrowerID.equals(cbCustId) ? null : borrowerID);
                                jcd.setActive(1);
                                jcd.setError(sd.getError());
                                // jcd.setMasterSub(cloneMaster);
                                // jcd.setBorrowerSub(borrower);
                                jcd.setRelation(sd.getRelation());
                                jointCustList.add(jcd);

                            });
                        } catch (Exception e) {
                            cloneMaster.setError(FinalData.EXIM_DATA_ERROR);
                            gErrorList.add(new GError(ErrorCat.MAJOR, ErrorDef.INVALID_GRP_MEM, custId, "Borrower, Co-Borrower mismatch."));
                        }
                    } else {
                        cloneMaster.setError(FinalData.EXIM_DATA_ERROR);
                        gErrorList.add(new GError(ErrorCat.MAJOR, ErrorDef.INVALID_GRP_MEM, custId, "No Borrower or Co-Borrower Found!"));
                    }
                }


                // Error Setting for MasterData and GroupMember
                if (!cloneMaster.getError().equals(FinalData.EXIM_DATA_CLEAN)) {
                    childrenList.forEach(grpData -> {
                        if (grpData.getError().equals(FinalData.EXIM_DATA_CLEAN)) {
                            grpData.setError(FinalData.EXIM_DATA_MASTER_ERROR);
                        }
                    });
                } else if (groupError) {
                    cloneMaster.setError(FinalData.EXIM_DATA_GROUP_ERROR);
                    childrenList.forEach(grpData -> {
                        if (grpData.getError().equals(FinalData.EXIM_DATA_CLEAN)) {
                            grpData.setError(FinalData.EXIM_DATA_GROUP_ERROR);
                        }
                    });
                }
                dataList.add(cloneMaster);
                if (!CollectionUtils.isEmpty(dataList) && !CollectionUtils.isEmpty(childrenList)) {
                    dataList.addAll(childrenList);
                }

            } catch (Exception e) {
                data.setError(FinalData.EXIM_DATA_ERROR);
            }

            /*Session session = TxController.getTxSession(txID).getSession();
            if (session != null && session.isOpen()) {
                session.flush();
                session.close();
            }*/
            txCO.rollbackPersistence(txID);
            return dataList;
        } catch (PersistentLayerException e) {
            try {
                txCO.rollbackPersistence(txID);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }

    public List<SubjectData> getGrpCustSubjectData(final String grpMasterId, SubjectData masterData, EibsDL eibsDL) {
        List<SubjectData> dataList = new ArrayList<>(0);
        String masterCustID = masterData.getCustId();
        try {
            List<GrpMemberPO> grpCustPOList = eibsDL.getGroupCustPOList(masterCustID);
            List<String> grpCustIDList = eibsDL.getGroupCustIdList(masterCustID);
            List<CustomerPO> grpCustList = eibsDL.findAllCustomerByByCustId(grpCustIDList);
            if (CollectionUtils.isEmpty(grpCustIDList) || CollectionUtils.isEmpty(grpCustList)) {
                sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.INVALID_GRP_MEM, masterData, "No Group Member Found !"));
                masterData.setError(FinalData.EXIM_DATA_ERROR);
                return dataList;
            } else if (grpCustIDList.size() > grpCustList.size()) {
                sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.INVALID_GRP_MEM, masterData, (grpCustIDList.size() - grpCustList.size()) + " Duplicate Group Member Found !"));
                masterData.setError(FinalData.EXIM_DATA_ERROR);
                return dataList;
            } else if (custType == CustType.JOINT) {
                // Checking-up: 1. Single Borrower, 2. Wrong Relation 3. Duplicate Relation
                ActionResult result = eibsDL.validateJointCustomer(masterCustID);
                if (!result.isSuccess()) {
                    sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.INVALID_GRP_MEM, masterData, result.getMsg()));
                    masterData.setError(FinalData.EXIM_DATA_ERROR);
                    return dataList;
                }
            }

            int c = 0;
            this.groupError = false;
            for (CustomerPO customer : grpCustList) {
                custId = customer.getCustID();
                custType = CustType.get(custNoObj.getCustomerType(custId));
                String custBrCode = customer.getBranchCode();
                if (!custBrCode.equals(brCode)) {
                    gErrorList.add(new GError(ErrorCat.SUSPICIOUS, ErrorDef.MISS_ENTRY, custId, "Customer Br Code not matched"));
                    //continue; // Having no branch
                }
                data = new SubjectData();
                boolean ok = transferData_GENERAL(customer);

                data.setGrpCustId(grpMasterId);
                data.setRelation(grpCustPOList.get(c).getRelation());
                data.setGroupSerial(c + 1);

                if (custType == CustType.INDIVIDUAL) {
                    data.setRecordType(CustType.INDIVIDUAL.RECORD_TYPE);
                    IndividualCustomerPO indCust = eibsDL.getIndividualCustomerByCustId(custId);
                    if (indCust != null) {
                        transferData_GENERAL(indCust);
                        transferData_P(indCust);
                    }
                } else {
                    // PR17001
                    data.setError(FinalData.EXIM_DATA_ERROR);
                    gError = new GError(ErrorCat.MAJOR, ErrorDef.INVALID_GRP_MEM, custId, "Individual Group member is expected. Found, " + custType.toString() + " Member.");
                    gErrorList.add(gError);
                }
                groupError = groupError || data.getError().equals(FinalData.EXIM_DATA_ERROR); // This line is responsible for marking error if any of the group member ConData contains error. If so the principal data treated/set as error.
                dataList.add(data);
                c++;
            }
            return dataList;
        } catch (Exception e) {
            // e.printStackTrace();
            return new ArrayList<>(0);
        }
    }

    public List<SubjectData> getGuarantorSubjectData(final String grpMasterId, SubjectData masterData, EibsDL eibsDL) throws Exception {
        List<SubjectData> dataList = new ArrayList<>(0);
        try {
            String masterCustID = masterData.getCustId();

            List<String> guraCustIdList = eibsDL.getGuarantorCustIDList(masterCustID);

            if (CollectionUtils.isEmpty(guraCustIdList)) {
                return new ArrayList<>(0);
            }
            List<CustomerPO> grpCustList = eibsDL.findAllCustomerByByCustId(guraCustIdList);

            int c = 0;
            this.groupError = false;
            for (CustomerPO customer : grpCustList) {
                try {
                    custId = customer.getCustID();
                    custType = CustType.get(custNoObj.getCustomerType(custId));
                    String custBrCode = customer.getBranchCode();
                    if (!custBrCode.equals(brCode)) {
                        gError = new GError(ErrorCat.SUSPICIOUS, ErrorDef.MISS_ENTRY, custId, "Customer Br Code not matched");
                        gErrorList.add(gError);
                        //continue; // Having no branch
                    }
                    data = new SubjectData();
                    boolean ok = transferData_GENERAL(customer);

                    data.setGrpCustId(grpMasterId);
                    data.setRelation(FinalData.REL_GUARANTOR);
                    data.setGroupSerial(c + 1);

                    if (custType == CustType.INDIVIDUAL) {
                        data.setRecordType(CustType.INDIVIDUAL.RECORD_TYPE);
                        IndividualCustomerPO indCust = eibsDL.getIndividualCustomerByCustId(custId);
                        if (indCust != null) {
                            transferData_GENERAL(indCust);
                            transferData_P(indCust);
                        }
                    } else {
                        data.setError(FinalData.EXIM_DATA_ERROR);
                        gError = new GError(ErrorCat.MAJOR, ErrorDef.INVALID_GRP_MEM, custId, "Temporarily, only Individual Group member is Allowed.");
                        gErrorList.add(gError);
                    }
                    groupError = groupError || data.getError().equals(FinalData.EXIM_DATA_ERROR); // This line is responsible for marking error if any of the group member ConData contains error. If so the principal data treated/set as error.
                    c++;
                } catch (Exception e) {
                    data.setError(FinalData.EXIM_DATA_ERROR);
                    masterData.setError(FinalData.EXIM_DATA_ERROR);
                }
                dataList.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;

    }

    public boolean transferData_GENERAL(CustomerPO customer) {
        try {
            data.setRecordType(custType.RECORD_TYPE);
            data.setError(FinalData.EXIM_DATA_CLEAN);
            data.setWriteToFile(FinalData.EXIM_DATA_STATUS_NOT_WRITE);
            data.setCibEligible(FinalData.SUB_DATA_NOT_ELIGIBLE);
            data.setCustType(custType.CODE);
            data.setCustId(custId);
            if (!customer.getBranchCode().equals(brCode)) {
                sErrorList.add(new SError(ErrorCat.SUSPICIOUS, ErrorDef.MISS_ENTRY, data, DDD.SP_1213_XN_4));
            }
            data.setBrCode(brCode);
            data.setGrpCustId("");
            data.setRelation(0);
            data.setSectorCode(rectifySectorCode(customer.getBbSector(), DDD.SP_1224_NN_6));
            //data.setTin(rectifyTIN(customer.getTin(), custType == CustType.INDIVIDUAL ? DDD.SP_1231_XN_12 : DDD.SC_1322_XN_12));

            if (custType == CustType.INDIVIDUAL) {
                NAME_LENGTH = 70;
                data.setTitle(rectifyTitle(customer.getCustTitle(), DDD.SP_1215_XN_20));
                data.setName(rectifyName(customer.getCustName(), DDD.SP_1216_XM_70, true));
                data.setPhoneNo(rectifyPhNO(customer.getOfficeTel(), DDD.SP_1248_XN_40));
                data.setCellNo(rectifyPhNO(customer.getCell(), DDD.SP_1248_XN_40));

                // Mailing Address as Present Address
                data.setPresentAddress(rectifyAddress(customer.getMailingAddr(), DDD.SP_1236_XD_100, false));
                data.setPresentPC(rectifyPostCode(customer.getMailingPostCode(), DDD.SP_1237_NN_4));
                String[] prDArr = rectifyDistrict(customer.getMailingDist(), DDD.SP_1238_XD_20, false);
                data.setPrdCode(prDArr[0]);
                data.setPresentDist(prDArr[1]);
                String[] prcArr = rectifyCountry(customer.getMailingCountry(), DDD.SP_1239_XD_2, false);
                data.setPrcCode(prcArr[0]);
                data.setPresentCountry(prcArr[1]);

            } else {
                NAME_LENGTH = 130;
                if (custType == CustType.PROPRIETORSHIP) {
                    data.setTitle(rectifyTitle(customer.getCustTitle(), DDD.SI_1415_XN_20));
                    data.setName(rectifyName(customer.getCustName(), DDD.SI_1416_XM_130, true));
                    data.setPhoneNo(rectifyPhNO(customer.getBusinessNo(), DDD.SI_1433_XN_40));
                    data.setCellNo(rectifyPhNO(customer.getCell(), DDD.SI_1433_XN_40));

                    // Mailing Address as Business Address
                    data.setBusinessAddress(rectifyAddress(customer.getMailingAddr(), DDD.SI_1423_XM_100, true));
                    data.setBusinessPC(rectifyPostCode(customer.getMailingPostCode(), DDD.SI_1424_NN_4));
                    String[] bizDArr = rectifyDistrict(customer.getMailingDist(), DDD.SI_1425_XM_20, true);
                    data.setBzdCode(bizDArr[0]);
                    data.setBusinessDist(bizDArr[1]);
                    String[] bizCArr = rectifyCountry(customer.getMailingCountry(), DDD.SI_1426_XM_2, true);
                    data.setBzcCode(bizCArr[0]);
                    data.setBusinessCountry(bizCArr[1]);
                } else {
                    data.setTitle(rectifyTitle(customer.getCustTitle(), DDD.SC_1315_XN_20));
                    data.setName(rectifyName(customer.getCustName(), DDD.SC_1316_XM_130, true));
                    data.setPhoneNo(rectifyPhNO(customer.getBusinessNo(), DDD.SC_1333_XN_40));
                    data.setCellNo(rectifyPhNO(customer.getCell(), DDD.SC_1333_XN_40));

                    // Mailing Address as Business Address
                    data.setBusinessAddress(rectifyAddress(customer.getMailingAddr(), DDD.SC_1323_XM_100, true));
                    data.setBusinessPC(rectifyPostCode(customer.getMailingPostCode(), DDD.SC_1324_NN_4));
                    String[] bizDArr = rectifyDistrict(customer.getMailingDist(), DDD.SC_1325_XM_20, true);
                    data.setBzdCode(bizDArr[0]);
                    data.setBusinessDist(bizDArr[1]);
                    String[] bizCArr = rectifyCountry(customer.getMailingCountry(), DDD.SC_1326_XM_2, true);
                    data.setBzcCode(bizCArr[0]);
                    data.setBusinessCountry(bizCArr[1]);
                }
            }
            return true;
        } catch (Exception e) {
            data.setError(FinalData.EXIM_DATA_ERROR);
            data.setErrorCode(FinalData.ERROR_CODE_IMPORT_FAILED_GD);
            return false;
        }

    }

    public boolean transferData_CI(CustomerPO customer) {
        try {
            // TODO... rectify
            data.setLegalForm(rectifyLegalForm(customer.getLegalForm(), DDD.SC_1319_NM_2));  // digit
            data.setRegNo(RectifyUtil.cutOrPadRight(customer.getRjscRegNo(), 15, PAD_STR_S));
            data.setRegDate(customer.getRjscRegDate());
            data.setCrgScore(customer.getCreditRiskGrad().intValue());
            data.setCreditRating(customer.getCreditRating().intValue());
            return true;
        } catch (Exception e) {
            data.setErrorCode(FinalData.ERROR_CODE_IMPORT_FAILED_CIDF);
            data.setError(FinalData.EXIM_DATA_ERROR);
            return false;
        }
    }

    public boolean transferData_GENERAL(IndividualCustomerPO indCust) {
        try {
            // Business Address
            data.setBusinessAddress(rectifyAddress(indCust.getBusAddress(), DDD.SP_1240_XD_100, false));
            data.setBusinessPC(rectifyPostCode(indCust.getBusPostCode(), DDD.SP_1241_NN_4));
            String[] bzdArr = rectifyDistrict(indCust.getBusDistrict(), DDD.SP_1242_XD_20, false);
            data.setBzdCode(bzdArr[0]);
            data.setBusinessDist(bzdArr[1]);
            String[] bzcArr = rectifyCountry(indCust.getBusCountry(), DDD.SP_1243_XD_2, false);
            data.setBzcCode(bzcArr[0]);
            data.setBusinessCountry(bzcArr[1]);
            return true;
        } catch (Exception e) {
            data.setError(FinalData.EXIM_DATA_ERROR);
            return false;
        }
    }

    public boolean transferData_P(IndividualCustomerPO indCust) {
        try {
            data.setFatherTitle(rectifyTitle(indCust.getFatherTitle(), DDD.SP_1217_XN_20));
            data.setFatherName(rectifyName(indCust.getFatherName(), DDD.SP_1218_XM_70, true));
            data.setMotherTitle(rectifyTitle(indCust.getMotherTitle(), DDD.SP_1219_XN_20));
            data.setMotherName(rectifyName(indCust.getMotherName(), DDD.SP_1220_XM_70, true));
            data.setSpouseTitle(rectifyTitle(indCust.getHusbandTitle(), DDD.SP_1221_XN_20));
            data.setSpouseName(rectifyName(indCust.getHusbandName(), DDD.SP_1222_XN_70, false));
            data.setSex(rectifyGender(indCust.getGender(), DDD.SP_1225_XM_1));

            //Birth Place
            data.setDob(rectifyDOB(indCust.getBirthDate(), DDD.SP_1226_NN_8));
            String birthCountry = indCust.getBirthCountry();
            String birthPlace = indCust.getBirthPlace();
            String[] bpcArr = rectifyCountry(birthCountry, DDD.SP_1228_XM_2, true);
            data.setBpcCode(GenericValidator.isBlankOrNull(bpcArr[0]) ? FinalData.COUNTRY_CODE_BD : bpcArr[0]); // Important! Must set before Birth Place
            data.setBirthCountry(GenericValidator.isBlankOrNull(bpcArr[0]) ? FinalData.COUNTRY_NAME_BD : bpcArr[1]); // Important! Must set before Birth Place
            String[] bpdArr = rectifyBirthPlace(data.getBpcCode(), birthPlace, DDD.SP_1227_XM_20);
            data.setBpdCode(bpdArr[0]);
            data.setBirthPlace(bpdArr[1]);

            String nid = rectifyNID(indCust.getNationalID(), DDD.SP_1229_XD_17);
            data.setNid(nid);
            boolean otherIdMandatory = GenericValidator.isBlankOrNull(nid);
            data.setNidAvailable(otherIdMandatory ? 0 : 1);

            // Permanent Address
            data.setPermanentAddress(rectifyAddress(indCust.getPermanentAddress(), DDD.SP_1232_XM_100, true));
            String perPC = indCust.getPermanentPostCode();
            data.setPermanentPC(rectifyPostCode(perPC, DDD.SP_1233_NN_4)); // OK
            String[] padArr = rectifyDistrict(indCust.getPermanentDistrict(), DDD.SP_1234_XM_20, true);
            data.setPedCode(padArr[0]);
            data.setPermanentDist(padArr[1]);
            String[] pacArr = rectifyCountry(indCust.getPermanentCountry(), DDD.SP_1235_XM_2, true);
            data.setPecCode(pacArr[0]);
            data.setPermanentCountry(pacArr[1]);

            Object[] idInfoArr = rectifyIDInfo(indCust, otherIdMandatory); // TODO.. Must
            data.setIdType((Integer) idInfoArr[0]);
            data.setIdRefNo((String) idInfoArr[1]);
            data.setIdIssueDate((Date) idInfoArr[2]);
            data.setIdIssueCC((String) idInfoArr[3]);
            data.setIdIssueCountry((String) idInfoArr[4]);
            return true;
        } catch (Exception e) {
            data.setError(FinalData.EXIM_DATA_ERROR);
            data.setErrorCode(FinalData.ERROR_CODE_IMPORT_FAILED_PD);
            return false;
        }

    }



    public String rectifyPhNO(String phoneNo, DDD ddc) {
        // TODO 1. add cell, 2. stripStart +88
        if (GenericValidator.isBlankOrNull(phoneNo)) {
            sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.BLANK, data, ddc));
            return ""; // cutOrPadRight("", PH_NO_LENGTH, PAD_STR_S);
        } else {
            phoneNo = phoneNo.toUpperCase();
            phoneNo = StringUtils.stripStart(phoneNo, "+88");
        }
        if (FinalData.SUSPICIOUS_TEXT_LIST.contains(phoneNo)) {
            sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.SUSPICIOUS_TEXT, data, ddc));
            phoneNo = "";
        }
        int len = phoneNo.length();
        return phoneNo.substring(0, len > PH_NO_LENGTH ? PH_NO_LENGTH : len); // cutOrPadRight(tin, PH_NO_LENGTH, PAD_STR_S);
    }

    public Integer rectifyLegalForm(String legalForm, DDD ddc) {
        if (GenericValidator.isBlankOrNull(legalForm)) {
            data.setError(FinalData.EXIM_DATA_ERROR);
            sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.BLANK, data, ddc));
            return null;
        } else {
            legalForm = legalForm.toUpperCase();
        }
        if (!GenericValidator.isInt(legalForm)) {
            data.setError(FinalData.EXIM_DATA_ERROR);
            sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.NaN, data, ddc));
            return null;
        }
        Integer lf = Integer.valueOf(legalForm);
        if (lf > 10 && lf < 1) {
            data.setError(FinalData.EXIM_DATA_ERROR);
            sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.NaN, data, ddc));
            return null;
        }
        return lf;
    }

    public Integer rectifyGender(Integer gender, DDD ddc) {
        if (gender < 1 || gender > 2) {
            data.setError(FinalData.EXIM_DATA_ERROR);
            sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.MISS_ENTRY, data, ddc));
            return gender;
        }
        return gender;
    }


    public String rectifySectorCode(String sectorCode, DDD ddc) {
        if (GenericValidator.isBlankOrNull(sectorCode)) {
            data.setError(FinalData.EXIM_DATA_ERROR);
            sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.UNEXPECTED, data, ddc));
            return ""; // 915051
        } else {
            sectorCode = sectorCode.trim();
            if (sectorCode.startsWith(FinalData.SECTOR_TYPE_PRIVATE)) {
                return sectorCode.length() != 6 ? FinalData.SECTOR_CODE_DEFAULT_PRIVATE : sectorCode;
            } else if (sectorCode.startsWith(FinalData.SECTOR_TYPE_PUBLIC)) {
                return sectorCode.length() != 6 ? FinalData.SECTOR_CODE_DEFAULT_PUBLIC : sectorCode;
            } else {
                data.setError(FinalData.EXIM_DATA_ERROR);
                sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.UNEXPECTED, data, ddc));
                return "";
            }
        }

    }

    public String rectifyTitle(String title, DDD ddc) {
        //List<String> titleList = Arrays.asList("MAULANA", "MOHD", "KAJI", "HAFEJ", " MAWLANA", "MOLLAH", "KAZI", "HAFEZ ", " MONSHI ", "MOLV", "KHAN MD", "HAFIJ", " MOSMT", "MORHUM ", "KHANDKER",  " MOULAVI", "MOSAMMAT", "KHONDOKAR", "ALHAJJ ", " MOULVI", "MOSAMMAT", "KHONDOKER", "ALHAZ", "MARHUM ", "MOSAMMOT", "KHANDOKER", "ALHAJ", "MASTER", "MOST", "SYED", "AL-HAJ", "MAU", "MOULANA ", "SAYED", "AL-HAZ",  "MOULOVI", "SAYEDA", "HAJI", "MISS", "MOW", "SHAH MD", "HAJEE", "MISSES", "MOWLVI", "SHAIK", "HAZI", "MISTRESS", "MR.", "SEIKH", "GAZI", "MLV", "MRS", "SHEIKH", "GAJI", "MOHAMMAD", "MST", "SHEIKH", "PIRJADA", "MOHAMMED", "MUHAMMAD", "SOYOD",  "MOHAMMOD", "MUHAMMED", "SHEKH",  "MOSHAMMAT", "MVW", "JANAB",  "SREEMOTI", "MOWLOVI", "JB",  "SREE", "MD.", "JBMOHD",  "ASS. PROFESSOR",  "LATEDR",  "ASSIST. PROFESSOR", "MAJOR",  "JUSTICE", "ASSOCIATE", "CAPT.",  "BARRISTER", "PROFESSOR", "GENERAL", "PRO", "ADV", "COL",  "ADVOCATE", "PROFESSORS", "(RETD)",   "DOCTOR", "BRIG GEN", "DR", "MAJ",   "ENG ", "MEJ",   "ENGG", "MIS", "ENGINEER", "MUSA.",   "ENGR");
        if (GenericValidator.isBlankOrNull(title)) {
            sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.BLANK, data, ddc));
            return ""; // cutOrPadRight("", TITLE_LENGTH, PAD_STR_S);
        } else if (!title.matches("^(.*?[a-zA-Z]){2,}.*$")) {
            sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.IMPERFECT, data, ddc, "Title should have at least 2 char."));
        }

        title = title.trim().toUpperCase();

        if (FinalData.SUSPICIOUS_TEXT_LIST.contains(title)) {
            sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.SUSPICIOUS_TEXT, data, ddc));
            title = "";
        }
        return title; // cutOrPadRight(title, TITLE_LENGTH, PAD_STR_S);
    }

    public String rectifyName(String name, DDD ddc, Boolean mandatory) {
        if (GenericValidator.isBlankOrNull(name)) {
            if (mandatory) {
                data.setError(FinalData.EXIM_DATA_ERROR);
                sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.BLANK, data, ddc));
            } else {
                sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.BLANK, data, ddc));
            }
            return "";
        } else if (!name.matches("^(.*?[a-zA-Z]){3,}.*$")) {
            if (mandatory) {
                data.setError(FinalData.EXIM_DATA_ERROR);
                sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.IMPERFECT, data, ddc, "Name should have at least 3 Alphabate."));
            } else {
                sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.BLANK, data, ddc));
            }
        }
        name = name.trim().toUpperCase();

        if (FinalData.SUSPICIOUS_TEXT_LIST.contains(name)) {
            if (mandatory) {
                data.setError(FinalData.EXIM_DATA_ERROR);
                sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.SUSPICIOUS_TEXT, data, ddc));
            } else {
                sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.SUSPICIOUS_TEXT, data, ddc));
                name = "";
            }
        }
        return name; // cutOrPadRight(name, NAME_LENGTH, PAD_STR_S);
    }

    public String rectifyAddress(String address, DDD ddc, boolean mandatory) {
        if (GenericValidator.isBlankOrNull(address)) {
            if (mandatory) {
                data.setError(FinalData.EXIM_DATA_ERROR);
                sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.BLANK, data, ddc));
            } else {
                sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.BLANK, data, ddc));
            }
            return "";
        } else {
            address = address.trim().toUpperCase();
            if (FinalData.SUSPICIOUS_TEXT_LIST.contains(address)) {
                if (mandatory) {
                    data.setError(FinalData.EXIM_DATA_ERROR);
                }
                sErrorList.add(new SError(mandatory ? ErrorCat.MAJOR : ErrorCat.MINOR, ErrorDef.SUSPICIOUS_TEXT, data, ddc));
                return RectifyUtil.cutOrPadRight(address, ADDRESS_LENGTH).trim();
            }
            int addressLen = address.length();
            if (addressLen > ADDRESS_LENGTH) {
                sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.LENGTH_EXCEEDED, data, ddc, "Address length found " + addressLen));
                return address.substring(0, ADDRESS_LENGTH);
            }
            return address;
        }
    }

    public String rectifyPostCode(String perPC, DDD ddc) {
        if (GenericValidator.isBlankOrNull(perPC)) {
            sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.NULL, data, ddc));
        } else if (FinalData.SUSPICIOUS_TEXT_LIST.contains(perPC)) {
            sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.SUSPICIOUS_TEXT, data, ddc));
        } else if (perPC.length() > PC_CODE_LENGTH) {
            sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.INVALID_LENGTH, data, ddc));
        } else if (!StringUtils.isNumeric(perPC)) {
            sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.NaN, data, ddc));
        } else {
            if (perPC.length() < PC_CODE_LENGTH) {
                sErrorList.add(new SError(ErrorCat.SUSPICIOUS, ErrorDef.INVALID_LENGTH, data, ddc));
            }
            return StringUtils.stripStart(perPC, "0"); // gender.replaceFirst("^0+(?!$)", ""); // cutOrPadLeft(gender, PC_CODE_LENGTH, "0");
        }
        return "";
    }

    public String[] rectifyDistrict(String district, DDD ddc, Boolean mandatory) {
        //String address = "";
        String[] result = new String[2];
        if (NumberUtils.isDigits(district)) {
            if (mandatory) {
                data.setError(FinalData.EXIM_DATA_ERROR);
                sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.INVALID_TYPE, data, ddc));
                return new String[]{"", ""};
            } else {
                sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.INVALID_TYPE, data, ddc));
                return new String[]{"0", ""};
            }
        }
        if (GenericValidator.isBlankOrNull(district)) {
            if (mandatory) {
                data.setError(FinalData.EXIM_DATA_ERROR);
                sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.BLANK, data, ddc));
                return new String[]{"", ""};
            } else {
                sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.BLANK, data, ddc));
                return new String[]{"0", ""};
            }
        } else {
            district = district.trim().toUpperCase().replaceAll(". ", "");
        }

        if (FinalData.SUSPICIOUS_TEXT_LIST.contains(district)) {
            if (mandatory) {
                data.setError(FinalData.EXIM_DATA_ERROR);
                sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.SUSPICIOUS_TEXT, data, ddc));
            } else {
                sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.SUSPICIOUS_TEXT, data, ddc));
            }
            return new String[]{"", district}; // TODO... birthPlace.substring(0, birthPlace.length() <= DIST_NAME_LENGTH ? birthPlace.length() : DIST_NAME_LENGTH);
        }

        String rectifiedCode = "";
        String rectifiedName = "";
        for (List<String> spellList : FinalData.DISTRICT_LIST_COMPLEX) {
            if (spellList.contains(district) || district.startsWith(spellList.get(2))) {
                String rightSpell = spellList.get(1);
                if (!rightSpell.equals(district)) {
                    sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.MISS_SPELLED, data, ddc, "\"" + district + "\" (in eIBS). Right Spell is \"" + rightSpell + "\"."));
                }
                rectifiedCode = spellList.get(0);
                rectifiedName = rightSpell;
                break;
            }
        }

        if (rectifiedCode.equals("")) {
            if (mandatory) {
                data.setError(FinalData.EXIM_DATA_ERROR);
                sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.MISS_SPELLED, data, ddc, FinalData.LINK_BB_DIST_LIST));
            } else {
                sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.MISS_SPELLED, data, ddc, FinalData.LINK_BB_DIST_LIST));
            }
           /*
            // Extract District fromAddress
            if (!GenericValidator.isBlankOrNull(address))
                for (String text : address.replaceAll(",", " ").replaceAll("-", " ").split(" ")) {
                    if (text.length() > 2) {
                        for (List<String> l : FinalData.DISTRICT_LIST_COMPLEX) {
                            if (l.contains(birthPlace)) {
                                rectifiedName = l.get(0) + "-" + l.get(1);
                                break;
                            }
                        }
                    }
                }
            if (rectifiedName.equals("")) {
                rectifiedName = birthPlace.trim().toLowerCase();
            }*/
            rectifiedCode = "0";
            rectifiedName = district;
        }
        result[0] = rectifiedCode;
        result[1] = rectifiedName.substring(0, rectifiedName.length() <= DIST_NAME_LENGTH ? rectifiedName.length() : DIST_NAME_LENGTH);
        return result;
    }

    public String[] rectifyCountry(String country, DDD ddc, boolean mandatory) {
        String[] result = new String[2];
        if (GenericValidator.isBlankOrNull(country)) {
            if (mandatory) {
                data.setError(FinalData.EXIM_DATA_ERROR);
                sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.BLANK, data, ddc));
                return new String[]{"", ""};
            } else {
                sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.BLANK, data, ddc));
                return new String[]{"", ""};
            }
        } else {
            country = country.trim().toUpperCase().replaceAll(". ", ""); // e.g. U. S. A
        }
        if (FinalData.SUSPICIOUS_TEXT_LIST.contains(country)) {
            if (mandatory) {
                data.setError(FinalData.EXIM_DATA_ERROR);
                sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.SUSPICIOUS_TEXT, data, ddc));
                return new String[]{"", ""};
            } else {
                sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.SUSPICIOUS_TEXT, data, ddc));
                return new String[]{"", ""}; // TODO.. return country.substring(0, country.length() <= COUNTRY_NAME_LENGTH ? country.length() : COUNTRY_NAME_LENGTH);
            }

        }

        String rectifiedCode = "";
        String rectifiedName = "";
        for (List<String> l : FinalData.COUNTRY_LIST_COMPLEX) {
            if (l.contains(country) || country.startsWith(l.get(2))) {
                rectifiedCode = l.get(0);
                rectifiedName = l.get(1);
                break;
            }
        }

        if (rectifiedCode.equals("")) {
            if (mandatory) {
                data.setError(FinalData.EXIM_DATA_ERROR);
                sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.MISS_SPELLED, data, ddc));
            } else {
                sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.MISS_SPELLED, data, ddc));
            }
            /*
            for (String text : address.replaceAll("[,-]", " ").split(" ")) {
                if (text.length() > 2) {
                    for (List<String> l : FinalData.COUNTRY_LIST_COMPLEX) {
                        if (l.contains(birthPlace)) {
                            rectifiedName = l.get(0) + "-" + l.get(1);
                            break;
                        }
                    }
                }
            }
            if (rectifiedName.equals("")) {
                rectifiedName = birthPlace.toLowerCase();
            }*/
            rectifiedCode = "";
            rectifiedName = country;
        }
        result[0] = rectifiedCode;
        result[1] = rectifiedName.substring(0, rectifiedName.length() <= COUNTRY_NAME_LENGTH ? rectifiedName.length() : COUNTRY_NAME_LENGTH);
        return result;
    }

    public String[] rectifyBirthPlace(String birthCountryCode, String birthPlace, DDD ddc) {
        /*if (GenericValidator.isBlankOrNull(birthCountryCode)) {
            data.setError(FinalData.SUB_DATA_STATUS_ERROR);
            return new String[]{"", RectifyUtil.cutOrPadRight(birthPlace, 20)};
        } else {
            if (!birthCountryCode.equals(FinalData.COUNTRY_CODE_BD)) {
                return new String[]{"", ""}; // TODO.. check value and substring
            } else {
                return rectifyDistrict(birthPlace, ddc, true);
            }
        }*/

        if (!GenericValidator.isBlankOrNull(birthCountryCode) && !birthCountryCode.equals(FinalData.COUNTRY_CODE_BD)) {
            return new String[]{"", RectifyUtil.cutOrPadRight(birthPlace, 20)}; // TODO.. check value and substring
        } else {
            return rectifyDistrict(birthPlace, ddc, true);
        }

    }

    public Date rectifyDOB(Date dob, DDD ddc) {
        if (dob == null) {
            sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.NULL, data, ddc));
        }
        return dob;
    }


    public String rectifyTIN(String tin, DDD ddc) {
        // TODO... remove '-' on rectification
        if (GenericValidator.isBlankOrNull(tin)) {
            sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.BLANK, data, ddc));
            return ""; // cutOrPadRight("", PH_NO_LENGTH, PAD_STR_S);
        } else {
            tin = tin.toUpperCase().replaceAll("-", "");
        }
        int len = tin.length();
        if (len > 12) {
            sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.LENGTH_EXCEEDED, data, ddc));
            tin = tin.substring(0, len > TIN_LENGTH + 18 ? TIN_LENGTH + 18 : len); // eIBS have 30 char data
        } else if (len < 12) {
            sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.INVALID_LENGTH, data, ddc));
        }
        if (FinalData.SUSPICIOUS_TEXT_LIST.contains(tin)) {
            sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.SUSPICIOUS_TEXT, data, ddc));
        }
        return tin; // cutOrPadRight(tin, PH_NO_LENGTH, PAD_STR_S);
    }


    public String rectifyNID(String nid, DDD ddc) {
        if (GenericValidator.isBlankOrNull(nid)) {
            sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.BLANK, data, ddc));
            return null;
        } else {
            nid = nid.trim();
        }
        if (!StringUtils.isNumeric(nid)) {
            sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.NaN, data, ddc));
            return null;
        }
        int len = nid.length();
        if (len == 13 || len == FinalData.NID_LENGTH_NEW_17) {
            if (len == 13) {
                nid = RectifyUtil.cutOrPadLeft(nid, FinalData.NID_LENGTH_NEW_17, RectifyUtil.PAD_STR_S);
            }
            return nid;
        } else {
            sErrorList.add(new SError(ErrorCat.MINOR, ErrorDef.INVALID_LENGTH, data, ddc));
            return null;
        }

    }


    /**
     * Getting ID (Passport, DL not Birth Certificate) information.
     * Passport having First Priority & Driving License (DL) having Second Priority
     * 'ID No', 'Issue Date', 'Issue Country Code' fields are mandatory
     *
     * @param po        IndividualCustomerPO
     * @param mandatory true If customers NID is not available
     * @return Object[]{ID Type, ID No, Issue Date, Issue Country Code, Issue Country Name};
     * @see CIBDictionary#ID_TYPE
     */
    public Object[] rectifyIDInfo(IndividualCustomerPO po, Boolean mandatory) {
        String passNo = po.getPassNo();
        Date passIssueDate = po.getIssueDate();
        String passIssuedFrom = po.getIssueFrom();
        // First Priority Passport
        if (GenericValidator.isBlankOrNull(passNo) || GenericValidator.isBlankOrNull(passNo.trim()) || passIssueDate == null || GenericValidator.isBlankOrNull(passIssuedFrom) || GenericValidator.isBlankOrNull(passIssuedFrom.trim())) {
            String dlNo = po.getDrvLicNo();
            Date dlIssueDate = po.getDrvIssueDate();
            String dlIssuedFrom = po.getDrvIssueFrom();
            // Second Priority Driving License
            if (GenericValidator.isBlankOrNull(dlNo) || GenericValidator.isBlankOrNull(dlNo.trim()) || dlIssueDate == null || GenericValidator.isBlankOrNull(dlIssuedFrom) || GenericValidator.isBlankOrNull(dlIssuedFrom.trim())) {
                if (mandatory) {
                    data.setError(FinalData.EXIM_DATA_ERROR);
                    sErrorList.add(new SError(ErrorCat.MAJOR, ErrorDef.BLANK, data, DDD.SP_1245_XD_20, "Any one of NID/Passport/Birth Certificate/Driving License is mandatory. And ID's Issue date and Issue Country is mandatory except NID."));
                }
            } else {
                dlNo = RectifyUtil.cutOrPadRight(dlNo.trim(), OTHER_ID_LENGTH);
                String[] cnc = rectifyCountry(dlIssuedFrom, DDD.SP_1247_XD_2, mandatory);
                String issueCC = GenericValidator.isBlankOrNull(cnc[0]) ? FinalData.COUNTRY_CODE_BD : cnc[0];
                String issueCountry = issueCC.equals(FinalData.COUNTRY_CODE_BD) ? FinalData.COUNTRY_NAME_BD : cnc[1];
                return new Object[]{FinalData.ID_TYPE_DL, dlNo, dlIssueDate, issueCC, issueCountry};
            }
        } else {
            passNo = RectifyUtil.cutOrPadRight(passNo.trim(), OTHER_ID_LENGTH);
            String[] cnc = rectifyCountry(passIssuedFrom, DDD.SP_1247_XD_2, mandatory);
            String issueCC = GenericValidator.isBlankOrNull(cnc[0]) ? FinalData.COUNTRY_CODE_BD : cnc[0];
            String issueCountry = issueCC.equals(FinalData.COUNTRY_CODE_BD) ? FinalData.COUNTRY_NAME_BD : cnc[1];
            return new Object[]{FinalData.ID_TYPE_PASS, passNo, passIssueDate, issueCC, issueCountry};
        }
        return new Object[]{null, "", null, "", ""};
    }

}

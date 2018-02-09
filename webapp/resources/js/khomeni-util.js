/**
 * Created by Khomeni on 20-Aug-17.
 */

/**
 * Added by: Khomeni
 * Added On: 28/09/2017
 * This function can be used to execute events on-ready the page
 * Example:
 *      IBBLReady(function(){
 *          // Your on-ready Script goes here.
 *          alert('DOM Ready!');
 *      });
 * */
function IBBLReady(f) {
    /in/.test(document.readyState) ? setTimeout('IBBLReady(' + f + ')', 9) : f()
}


IBBLReady(function () {
    $('.copy-me').click(function (e) {
        //copyToClipboard(e.target);
        copyTextToClipboardJavascript(e.target);
    });
});

/**
 * Added by: Khomeni
 * Added On: 20/10/2016
 * For Trim functionality. e.g: ' My String '.trim(); will returns 'My String'.
 * @returns {string}
 */
String.prototype.trim = function () {
    return this.replace(/^\s+|\s+$/g, "");
};
String.prototype.ltrim = function () {
    return this.replace(/^\s+/, "");
};
String.prototype.rtrim = function () {
    return this.replace(/\s+$/, "");
};

/**
 * Author: Khomeni
 * Call this function to set AT LEAST '0' on a number field;
 * Example: <input type="text" onkeyup="setZero(this)">
 * @param thisObj: Current DOM object
 */
function setZero(thisObj) {
    var thisVal = thisObj.value;
    if (thisVal == "" || isNaN(thisVal))
        thisObj.value = 0;
    else
        thisObj.value = eval(thisVal);
}


/**
 * Author: Khomeni
 * Implemented on : 02/10/2016
 * To: Create HTML DOM input, to append with the DOM form object
 *
 * Example:
 *          var myField = createHiddenField('custId', 21325454541);
 *
 *          var myForm = createForm('post', 'ctx/doSomething.do');
 *          myForm.appendChild(myField);
 *          myForm.submit();
 *
 * @param name. Name of the field. Like- <input name='???'/>
 * @param value. Value of the field. Like- <input value='???'/>
 * @returns {Element|*} HTML DOM input object
 */
function createHiddenField(name, value) {
    var myInput = document.createElement("input");
    myInput.type = "hidden";
    myInput.name = name;
    myInput.value = value;
    return myInput;
}


/**
 * Author: Khomeni
 * Implemented on : 02/10/2016
 * To: Create form to 'post' or 'get' request.
 * Example:
 *          var myForm = createForm('post', 'ctx/doSomething.do');
 *          myForm.submit();
 *          N.B. You can add element within the form to contain values.
 *          See 'createHiddenField(name, value) function'
 * @param method. The method name i.e. 'post'/'get'
 * @param url. The URL where to submit. e.g. something.do
 * @returns HTML DOM Form Object
 * Last Edited: 18/10/2016
 */
function createForm(method, url) {
    var submitBtn = document.createElement("input");
    submitBtn.type = 'submit';

    var myForm = document.createElement("form");
    myForm.method = method;
    myForm.action = url;
    // myForm.enctype = "multipart/form-data";
    //if (navigator.userAgent.search("Firefox") > -1) {
    myForm.appendChild(submitBtn);
    document.getElementsByTagName("body")[0].appendChild(myForm);
    // }

    return myForm;
}


/**
 * Author: Khomeni
 * To:  Create and post a Dynamic form
 * Example:
 *      onclick="postMe('ctx/hitMe.do', 'id=23 & type=special & cat=3 & status=')"
 *      N.B. In the second param, spaces (before and after '&' sign) won't raise any effect.
 * @param url
 * @param paramValuePairs
 */
function postMe(url, paramValuePairs) {
    var myForm = createForm('post', url);
    var pairArray = paramValuePairs.split("&");
    for (var i = 0; i < pairArray.length; i++) {
        var pv = pairArray[i].split("=");
        myForm.appendChild(createHiddenField(pv[0].trim(), pv.length < 2 ? '' : pv[1].trim()));
    }
    //console.log(myForm);
    myForm.submit();
}
/**
 * Author: Khomeni
 * To:  Create and post a Dynamic form and open in a new window
 * Example:
 *      onclick="postMe('ctx/hitMe.do', 'id=23&type=special&cat=3')"
 * @param url
 * @param paramValuePairs
 */
function postMe_blank(url, paramValuePairs) {
    var myForm = createForm('post', url);
    var pairArray = paramValuePairs.split("&");
    for (var i = 0; i < pairArray.length; i++) {
        var pv = pairArray[i].split("=");
        myForm.appendChild(createHiddenField(pv[0].trim(), pv[1].trim()));
    }
    myForm.target = "_blank";
    //console.log(myForm);
    myForm.submit();
}

function copyToClipboard(dom) {
    var $temp = $("<input>");
    $("body").append($temp);
    var copyText = $(dom).text().trim();
    $temp.val(copyText).select();
    var copied = document.execCommand("copy");
    $temp.remove();
    if (!copied) {
        copied = copyTextToClipboardJavascript(copyText);
    }


}

function copyTextToClipboardJavascript(dom) {
    var copyText = dom.innerHTML.trim();
    var textArea = document.createElement("textarea");
    textArea.style.position = 'fixed';
    textArea.style.top = 0;
    textArea.style.left = 0;
    textArea.style.width = '2em';
    textArea.style.height = '2em';
    textArea.style.padding = 0;
    textArea.style.border = 'none';
    textArea.style.outline = 'none';
    textArea.style.boxShadow = 'none';
    textArea.style.background = 'transparent';
    textArea.value = copyText;
    document.body.appendChild(textArea);
    textArea.select();
    var successful;
    try {
        successful = document.execCommand('copy');
    } catch (err) {
        successful = false;
    }
    document.body.removeChild(textArea);

    if (successful) {
        $.notify('<strong style="height: 300px; font-size: 15px">' + copyText + ' copied</strong>', {
            allow_dismiss: false,
            type: "warning",
            delay: 200, //showing duration
            timer: 1000, // loading duration
            placement: {
                from: "top",
                align: "center"
            },
            animate: {
                enter: 'animated zoomInUp',
                exit: 'animated zoomOutDown'
            }
        });
    }
}




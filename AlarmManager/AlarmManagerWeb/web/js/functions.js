function createDatepickers() {
    var startDateTextBox = $('#eventDate');
    var endDateTextBox = $('#endDate');

    startDateTextBox.datetimepicker({
        showSecond: true,
        dateFormat: 'dd/mm/yy',
        timeFormat: 'HH:mm:ss',
        onClose: function(dateText, inst) {
            if (endDateTextBox.val() != '') {
                var testStartDate = startDateTextBox.datetimepicker('getDate');
                var testEndDate = endDateTextBox.datetimepicker('getDate');
                if (testStartDate > testEndDate)
                    endDateTextBox.datetimepicker('setDate', testStartDate);
            }
            else {
                endDateTextBox.val(dateText);
            }
        },
        onSelect: function(selectedDateTime) {
            endDateTextBox.datetimepicker('option', 'minDate', startDateTextBox.datetimepicker('getDate'));
        }
    });
    endDateTextBox.datetimepicker({
        showSecond: true,
        dateFormat: 'dd/mm/yy',
        timeFormat: 'HH:mm:ss',
        onClose: function(dateText, inst) {
            if (startDateTextBox.val() != '') {
                var testStartDate = startDateTextBox.datetimepicker('getDate');
                var testEndDate = endDateTextBox.datetimepicker('getDate');
                if (testStartDate > testEndDate)
                    startDateTextBox.datetimepicker('setDate', testEndDate);
            }
            else {
                startDateTextBox.val(dateText);
            }
        },
        onSelect: function(selectedDateTime) {
            startDateTextBox.datetimepicker('option', 'maxDate', endDateTextBox.datetimepicker('getDate'));
        }
    });
}

function createNumberPicker(){
    var numberField = $('#repeatQuantity');
    numberField.spinner();
}

function numbersonly(myfield, e, dec){
    var key;
    var keychar;

    if (window.event)
        key = window.event.keyCode;
    else if (e)
        key = e.which;
    else
        return true;
    keychar = String.fromCharCode(key);

    // control keys
    if ((key==null) || (key==0) || (key==8) || 
        (key==9) || (key==13) || (key==27) )
        return true;

    // numbers
    else if ((("0123456789").indexOf(keychar) > -1))
        return true;

    // decimal point jump
    else if (dec && (keychar == "."))
    {
        myfield.form.elements[dec].focus();
        return false;
    }
    else
        return false;
}

function repeatedChecked(){
    if(document.getElementById("repeated").checked){
        enableRepeatedFields();
    }
    else {
        disableRepeatedFields();
    }
}

function disableRepeatedFields(){
    document.getElementById("repeatunit").disabled=true;
    document.getElementById("repeatQuantity").disabled=true;
    document.getElementById("endDate").disabled=true;
}

function enableRepeatedFields(){
    document.getElementById("repeatunit").disabled=false;
    document.getElementById("repeatQuantity").disabled=false;
    document.getElementById("endDate").disabled=false;
}

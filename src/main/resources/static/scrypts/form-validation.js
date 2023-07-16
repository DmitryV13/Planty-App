const formElement = document.getElementById('form');
const formInputs = document.querySelectorAll("input")
const formTextareas=document.querySelectorAll("textarea");
const fileNamespace = document.querySelector(".file-input-body .chosen-file");
const fileInput = document.querySelector(".file-input-body input");
const shortError = "Is too short";
const emptyError = "Should not be empty";
const extensionError="Extension should be one from: jpg, jpeg, png";
const extensions=["jpg","jpeg","png"];
const wateringError="Data should be in form: 1,2,3,..."

for (let i = 0; i < formInputs.length; i++) {
    formInputs[i].addEventListener('focusout', (e) => {
        //let errorTextElement = formInputs[i].nextElementSibling;
        const inputElement = e.target;
        if(inputElement===document.getElementById("plantWateringPeriods") || inputElement===document.getElementById("plantWateringAmount")){
            return;
        }
        let validityState = inputElement.validity;
        if (validityState.valid && inputElement.value.trim()!=="" && inputElement.value.trim().length>=8 || (inputElement===document.getElementById("plantPeriod") && validityState.valid)) {
            //errorTextElement.innerHTML="";
            inputElement.classList.remove("invalid");
            inputElement.classList.add("valid");
            return;
        } else {
            if (whatIsWrong(validityState, inputElement) === 0 || inputElement.value.trim()==="" || inputElement.value.trim().length<8) {
                if(inputElement.value.trim()===""){
                    inputElement.setCustomValidity(emptyError);
                }else if(inputElement.value.trim().length<8){
                    if(inputElement===document.getElementById("plantPeriod")){
                        inputElement.setCustomValidity("");
                        inputElement.checkValidity();
                        //errorTextElement.innerHTML="";
                        inputElement.classList.remove("invalid");
                        inputElement.classList.add("valid");
                        return;
                    }
                    inputElement.setCustomValidity(shortError);
                }
                inputElement.classList.remove("valid");
                inputElement.classList.add("invalid");
                //errorTextElement.textContent = inputElement.validationMessage;
            }
            else {
                inputElement.setCustomValidity("");
                inputElement.checkValidity();
                //errorTextElement.innerHTML="";
                inputElement.classList.remove("invalid");
                inputElement.classList.add("valid");
            }
        }
    });
}

for (let i = 0; i < formTextareas.length; i++) {
    formTextareas[i].addEventListener('focusout', (e) => {
        let textareaContent=e.target.closest(".textarea-content");
        //let errorTextElement = formInputs[i].nextElementSibling;
        const inputElement = e.target;
        let validityState = inputElement.validity;
        if (validityState.valid && inputElement.value.trim()!=="" && inputElement.value.trim().length>=20) {
            //errorTextElement.innerHTML="";
            textareaContent.classList.remove("invalid");
            textareaContent.classList.add("valid");
            return;
        } else {
            if (whatIsWrong(validityState, inputElement) === 0 || inputElement.value.trim()==="" || inputElement.value.trim().length<20) {
                if(inputElement.value.trim()===""){
                    inputElement.setCustomValidity(emptyError);
                }else if(inputElement.value.trim().length<20){
                    inputElement.setCustomValidity(shortError);
                }
                textareaContent.classList.remove("valid");
                textareaContent.classList.add("invalid");
                //errorTextElement.textContent = inputElement.validationMessage;
            }
            else {
                inputElement.setCustomValidity("");
                inputElement.checkValidity();
                //errorTextElement.innerHTML="";
                textareaContent.classList.remove("invalid");
                textareaContent.classList.add("valid");
            }
        }
    });
}

fileInput.addEventListener('input', (e) => {
    const formData = new FormData(formElement);
    const file = formData.get('file');
    if (file.name != "") {
        checkExtension(file);
        writeFileName(file, fileNamespace);
    }
});

formElement.addEventListener('submit', (e) => {
    const invalidFields = document.querySelectorAll(".invalid");
    if (invalidFields.length > 0) {
        e.preventDefault()
    }
})

function writeFileName(file, fileNamespace) {
    fileNamespace.textContent = file.name;
}

function checkExtension(file){
    let extensionErrorMessage = document.querySelector(".extension-error");
    let extension=file.name.split('.').pop().toLowerCase();
    if(extensions.indexOf(extension)===-1){
        fileInput.setCustomValidity(extensionError);
        extensionErrorMessage.textContent=fileInput.validationMessage;
        fileNamespace.classList.remove('valid')
        fileNamespace.classList.add("invalid")
        return false;
    }
    fileInput.setCustomValidity("");
    fileInput.checkValidity();
    extensionErrorMessage.innerHTML="";
    fileNamespace.classList.remove("invalid")
    fileNamespace.classList.add('valid')
    return true;
}

function whatIsWrong(validityState, inputElement) {
    if (validityState.valueMissing) {
        inputElement.setCustomValidity(emptyError);
        return 0;
    } else if (validityState.tooShort) {
        inputElement.setCustomValidity(shortError);
        return 0;
    } else if (validityState.customError) {
        return 1;
    }
}

const wateringKeys=document.getElementById("plantWateringPeriods");
const wateringValues=document.getElementById("plantWateringAmount");

wateringKeys.addEventListener('focusout',(e)=>{
    if(areValidKeys(wateringKeys)){
        wateringKeys.classList.remove("invalid");
        wateringKeys.setCustomValidity("");
        wateringKeys.checkValidity();
        wateringKeys.classList.add("valid");
    }else{
        wateringKeys.setCustomValidity(wateringError);
        wateringKeys.classList.remove("valid");
        wateringKeys.classList.add("invalid");
    }
})

wateringValues.addEventListener('focusout',(e)=>{
    if(areValidKeys(wateringValues)){
        wateringValues.classList.remove("invalid");
        wateringValues.setCustomValidity("");
        wateringValues.checkValidity();
        wateringValues.classList.add("valid");
    }else{
        wateringValues.setCustomValidity(wateringError);
        wateringValues.classList.remove("valid");
        wateringValues.classList.add("invalid");
    }
})

function areValidKeys(wateringEl){
    let stringArrayKey=wateringEl.value.split(',');
    for (let i = 0; i < stringArrayKey.length; i++) {
        if(stringArrayKey[i]==="false" || stringArrayKey[i]==="true" || isNaN(Number(stringArrayKey[i]))){
            return false;
        }
    }
    return true;
}
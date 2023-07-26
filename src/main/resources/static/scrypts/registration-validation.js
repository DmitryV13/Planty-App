const formElement = document.querySelector("form");
const formInputs = document.querySelectorAll("input");
const shortError = "Is too short";
const emptyError = "Should not be empty";

for (let i = 0; i < formInputs.length; i++) {
    formInputs[i].addEventListener('focusout', (e) => {
        const inputElement = e.target;
        let validityState = inputElement.validity;
        if (validityState.valid && inputElement.value.trim()!=="" && inputElement.value.trim().length>=8) {
            inputElement.closest(".service-form-item").classList.remove("invalid");
            return;
        } else {
            if (whatIsWrong(validityState, inputElement) === 0 || inputElement.value.trim()==="" || inputElement.value.trim().length<8) {
                if(inputElement.value.trim()===""){
                    inputElement.setCustomValidity(emptyError);
                }
                inputElement.closest(".service-form-item").classList.add("invalid");
            }
            else {
                inputElement.setCustomValidity("");
                inputElement.checkValidity();
                inputElement.closest(".service-form-item").classList.remove("invalid");
            }
        }
    });
}

formElement.addEventListener('submit', (e) => {
    const invalidFields = document.querySelectorAll(".invalid");
    for (let i = 0; i < formInputs.length; i++) {
        const inputElement=formInputs[i];
        let validityState = inputElement.validity;
        if(!validityState.valid || inputElement.value.trim()==="" && inputElement.value.trim().length<8) {
            e.preventDefault();
            whatIsWrong(validityState, inputElement);
                if(inputElement.value.trim()===""){
                    inputElement.setCustomValidity(emptyError);
                }
                inputElement.closest(".service-form-item").classList.add("invalid");
            return;
        }
    }
    if (invalidFields.length > 0) {
        e.preventDefault();
    }
});

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
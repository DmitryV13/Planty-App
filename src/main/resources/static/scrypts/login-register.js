//PLACEHOLDERS
const input = document.querySelectorAll(".form-input");

for (let i = 0; i < input.length; i++) {
    const el = input[i];
    el.addEventListener("focus", function (e) {
        const placeholderName = el.getAttribute("id");
        const placeholder = document.querySelector(".placeholder-" + placeholderName);//label
        changePosition(placeholder, placeholderName);
    })
}

function changePosition(placeholder, placeholderName) {
    placeholder.classList.add("target");
    const input=document.getElementById(placeholderName);
    input.addEventListener("focusout", function (e) {
        if(input.value.trim()==="") {
            resetPosition(placeholder);
        }
    })
}

function resetPosition(placeholder) {
    placeholder.classList.remove("target");
}

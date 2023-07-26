const input = document.querySelectorAll("textarea");

for (let i = 0; i < input.length; i++) {
    const el = input[i];
    el.addEventListener("focus", function (e) {
        const textareaContent = e.target.closest(".textarea-content");
        getFocus(textareaContent,el);
    })
}

function getFocus(textareaContent,el) {
    textareaContent.classList.add("active");
    el.addEventListener("focusout", function (e) {
        const textareaContent = e.target.closest(".textarea-content");
        loseFocus(textareaContent);
    })
}

function loseFocus(textareaContent) {
    textareaContent.classList.remove("active");
}
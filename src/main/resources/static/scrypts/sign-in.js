//POPUPS
const popupLinks = document.querySelectorAll(".popup-link");
const body = document.querySelector("body");
const lockPadding = document.querySelectorAll(".lock__padding")
const popupCloseIcon = document.querySelectorAll(".popup__close");

let unlock = true;

const timeout = 800;

if (popupCloseIcon.length > 0) {
    for (let i = 0; i < popupCloseIcon.length; i++) {
        const el = popupCloseIcon[i];
        el.addEventListener("click", function (e) {
            popupClose(el.closest(".popup"))
            e.preventDefault();
        })
    }
}

if (popupLinks.length > 0) {
    for (let i = 0; i < popupLinks.length; i++) {
        const popupLink = popupLinks[i];
        popupLink.addEventListener("click", function (e) {
            const popupName = popupLink.getAttribute("href").replace("#", "");
            const currentLink = document.getElementById(popupName);
            popupOpen(currentLink);
            e.preventDefault();
        })
    }
}

function popupOpen(currentLink) {
    if (currentLink && unlock) {
        const popupOpen = document.querySelector(".open");
        if (popupOpen) {
            popupClose(popupOpen, false);
        } else {
            bodyLock();
        }
        currentLink.classList.add("open");
        currentLink.addEventListener("click", function (e) {
            if (!e.target.closest(".popup__content")) {
                popupClose(e.target.closest(".popup"));
            }
        })
    }
}

function popupClose(currentPopup, doUnlock = true) {
    if (unlock) {
        currentPopup.classList.remove("open");
        if (doUnlock) {
            bodyUnlock();
        }
    }
}

function bodyLock() {
    const lockPaddingValue = window.innerWidth - document.querySelector(".wrapper").offsetWidth + "px";
    if (lockPadding.length > 0) {
        for (let i = 0; i < lockPadding.length; i++) {
            const el = lockPadding[i];
            el.style.paddingRight = lockPaddingValue;
        }
    }
    body.style.paddingRight = lockPaddingValue;
    body.classList.add("locked");

    unlock = false;
    setTimeout(function () {
        unlock = true;
    }, timeout);
}

function bodyUnlock() {
    setTimeout(function () {
        for (let i = 0; i < lockPadding.length; i++) {
            const el = lockPadding[i];
            el.style.paddingRight = "0px";
        }
        body.style.paddingRight = "0px";
        body.classList.remove("locked");
    }, timeout);

    unlock = false;
    setTimeout(function () {
        unlock = true;
    }, timeout);
}


//cross-browser compatibility
(function () {
    if (!Element.prototype.closest) {
        Element.prototype.closest = function (css) {
            var node = this;
            while (node) {
                if (node.matches(css)) return node;
                else node = node.parentElement;
            }
            return null;
        };
    }
})();

(function () {
    if (!Element.prototype.matches) {
        Element.prototype.matches = Element.prototype.matchesSelector ||
            Element.prototype.webkitMatchesSelector ||
            Element.prototype.mozMatchesSelector ||
            Element.prototype.msMatchesSelector;
    }
})();
//


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

//EMPTY-SPACES

//ASYNCHRONOUS PAGE'S UPDATE
// $.ajax({
//     url: "/login",
//     method: "GET",
//     data: {
//         username: "your-username",
//         password: "your-password"
//     },
//     success: function(response) {
//         if (response === "redirect:/") {
//             var error = $(".sign-in-form-error");
//             error.html(response);
//             error.addClass("isPresent");
//         } else {
//             window.location.href = "/myGarden";
//         }
//     }
// });
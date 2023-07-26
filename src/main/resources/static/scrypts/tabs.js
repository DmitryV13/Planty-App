const tasksLinks = document.querySelectorAll(".tasks-link");
let taskLine = document.querySelector(".tab-line");

for (let i = 0; i < tasksLinks.length; i++) {
    const tasksEl = tasksLinks[i];
    tasksEl.addEventListener("click", function (e) {
        taskLine.style.width = e.target.offsetWidth + "px";
        taskLine.style.left = e.target.offsetLeft + "px";
        let tasksVis = document.querySelector(".visible");
        addInvisibility(tasksVis);
        const taskInvId = tasksEl.getAttribute("href").replace("#", "");
        let taskInv = document.getElementById(taskInvId);
        addVisibility(taskInv);
        e.preventDefault();
    })
}

function addInvisibility(tasksInv) {
    tasksInv.classList.remove("visible")
    tasksInv.classList.add("invisible")
}

function addVisibility(taskVis) {
    taskVis.classList.remove("invisible")
    taskVis.classList.add("visible")
}
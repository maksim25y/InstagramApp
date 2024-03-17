"use strict";
//скролл
const smoothLinks = document.querySelectorAll("a[href^='#']");
for (let smoothLink of smoothLinks) {
  smoothLink.addEventListener("click", function (e) {
    e.preventDefault();
    const id = smoothLink.getAttribute("href");
    document.querySelector(id).scrollIntoView({
      behavior: "smooth",
      block: "start",
    });
  });
}
//гамбургер
window.addEventListener("DOMContentLoaded", () => {
  const menu = document.querySelector(".nav-menu"),
    menuItem = document.querySelectorAll(".nav-menu__list-item"),
    hamburger = document.querySelector(".hamburger");

  hamburger.addEventListener("click", () => {
    hamburger.classList.toggle("hamburger_active");
    menu.classList.toggle("nav-menu_active");
  });

  menuItem.forEach((item) => {
    item.addEventListener("click", () => {
      hamburger.classList.toggle("hamburger_active");
      menu.classList.toggle("nav-menu_active");
    });
  });
});
// Модальное окошко при клике на кнопку
let btnM = document.querySelectorAll(".buttons__main-btn");
const modalWindow = document.querySelector(".modal");
const overlay = document.querySelector(".overlay");
const closeModals = document.querySelectorAll(".modal__close-modal");
const modalBtn = document.querySelectorAll(".modal__btn");
const linkBtn = document.querySelector(".link-btn");
for (let value of btnM) {
  value.addEventListener("click", function () {
    modalWindow.classList.toggle("hidden");
    overlay.classList.toggle("hidden");
  });
}
linkBtn.addEventListener("click", function () {
  modalWindow.classList.toggle("hidden");
  overlay.classList.toggle("hidden");
});
overlay.addEventListener("click", function () {
  if (!overlay.classList.contains("hidden")) {
    modalWindow.classList.toggle("hidden");
    overlay.classList.toggle("hidden");
  }
});
for (let value of closeModals) {
  value.addEventListener("click", function () {
    modalWindow.classList.toggle("hidden");
    overlay.classList.toggle("hidden");
  });
}
for (let value of modalBtn) {
  value.addEventListener("click", function () {
    let res = document.querySelectorAll(".modal__data");
    console.log(res.text);
    value.style.background = "green";
    value.textContent = "Вы записаны";
  });
}
// if (overlay.classList.contains("hidden") == false) {
//   overlay.addEventListener("click", function () {
//     modalWindow.classList.toggle("hidden");
//     overlay.classList.toggle("hidden");
//   });
// }

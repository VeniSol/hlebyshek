const urlParams = new URLSearchParams(window.location.search);
if (urlParams.has('error')) {
    const home = document.querySelector(".home");
    home.classList.add("show")
    document.getElementById('err').textContent = 'Неверный логин или пароль';
}
if (urlParams.has('errorSign')) {
    const home = document.querySelector(".home");
    const form_container = document.querySelector(".form_container");
    home.classList.add("show")
    form_container.classList.add("active")
    document.getElementById('err2').textContent = 'Аккаунт с таким логином уже существует';
}

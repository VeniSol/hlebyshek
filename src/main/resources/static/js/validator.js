const urlParams = new URLSearchParams(window.location.search);
if (urlParams.has('error')) {
    const home = document.querySelector(".home");
    home.classList.add("show")
    document.getElementById('err').textContent = 'Неверный логин или пароль';
}

function togglePassword() {
    var passwordInput = document.getElementById("password");
    if (passwordInput.type === "password") {
        passwordInput.type = "text";
    } else {
        passwordInput.type = "password";
    }
}

function validateForm(event) {
    event.preventDefault(); // prevents default form submission

    const email = document.getElementById('email');
    const password = document.getElementById('password');
    const emailError = document.getElementById('emailError');
    const passwordError = document.getElementById('passwordError');
    let valid = true;

    // Reset lại các lỗi ban đầu
    email.classList.remove('is-invalid');
    password.classList.remove('is-invalid');
    emailError.textContent = '';
    passwordError.textContent = '';

    // Check email
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (email.value === '') {
        emailError.textContent = 'Email không được để trống.';
        email.classList.add('is-invalid');
        valid = false;
    } else if (!emailPattern.test(email.value)) {
        emailError.textContent = 'Email không đúng định dạng.';
        email.classList.add('is-invalid');
        valid = false;
    }

    // check password
    if (password.value === '') {
        passwordError.textContent = 'Mật khẩu không được để trống.';
        password.classList.add('is-invalid');
        valid = false;
    } else if (password.value.length < 8) {
        passwordError.textContent = 'Mật khẩu phải có ít nhất 8 ký tự.';
        password.classList.add('is-invalid');
        valid = false;
    } else if (!/[A-Z]/.test(password.value)) {
        passwordError.textContent = 'Mật khẩu phải có ít nhất 1 ký tự viết hoa.';
        password.classList.add('is-invalid');
        valid = false;
    } else if (!/[!@#$%^&*]/.test(password.value)) {
        passwordError.textContent = 'Mật khẩu phải có ít nhất 1 ký tự đặc biệt (@, #, ...).';
        password.classList.add('is-invalid');
        valid = false;
    }

    if (valid) {
        document.getElementById('loginForm').submit();
    }
}
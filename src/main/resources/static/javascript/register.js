function validateRegisterForm(event) {
    event.preventDefault(); // Prevents default form submission

    const email = document.getElementById('email');
    const password = document.getElementById('password');
    const confirmPassword = document.getElementById('confirmPassword');
    const userName = document.getElementById('userName');
    const phone = document.getElementById('phone');
    const address = document.getElementById('address');

    const emailError = document.getElementById('emailError');
    const passwordError = document.getElementById('passwordError');
    const confirmPasswordError = document.getElementById('confirmPasswordError');
    const userNameError = document.getElementById('userNameError');
    const phoneError = document.getElementById('phoneError');
    const addressError = document.getElementById('addressError');

    let valid = true;

    // reset error
    email.classList.remove('is-invalid');
    password.classList.remove('is-invalid');
    confirmPassword.classList.remove('is-invalid');
    userName.classList.remove('is-invalid');
    phone.classList.remove('is-invalid');
    address.classList.remove('is-invalid');

    emailError.textContent = '';
    passwordError.textContent = '';
    confirmPasswordError.textContent = '';
    userNameError.textContent = '';
    phoneError.textContent = '';
    addressError.textContent = '';

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

    // Check password
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

    // Check repeat password
    if (confirmPassword.value !== password.value) {
        confirmPasswordError.textContent = 'Mật khẩu nhập lại không khớp.';
        confirmPassword.classList.add('is-invalid');
        valid = false;
    }

    // Check username
    if (userName.value.trim() === '') {
        userNameError.textContent = 'Tên người dùng không được để trống.';
        userName.classList.add('is-invalid');
        valid = false;
    }

    // Check phone number
    if (phone.value.trim() === '' || !/^[0-9]{10,11}$/.test(phone.value)) {
        phoneError.textContent = 'Số điện thoại không hợp lệ.';
        phone.classList.add('is-invalid');
        valid = false;
    }

    // Check address
    if (address.value.trim() === '') {
        addressError.textContent = 'Địa chỉ không được để trống.';
        address.classList.add('is-invalid');
        valid = false;
    }

    if (valid) {
        document.getElementById('registerForm').submit(); // Submit when infor were valid
    }
}
for (const form of document.querySelectorAll('form[data-validate]')) {
    const fields = Array.from(form.querySelectorAll('input, textarea'))
        .filter(field => field.type !== 'hidden' && !field.disabled);
    let attempted = false;

    function validateField(field) {
        const value = field.value;
        const rules = field.dataset;

        if ('selection' in rules && !/^[1-9][0-9]*$/.test(value)) {
            return 'Vui lòng chọn danh mục cần sửa trong bảng.';
        }
        if (field.validity.valueMissing || ('notBlank' in rules && !value.trim())) {
            return 'Vui lòng nhập hoặc chọn thông tin này.';
        }
        if (field.maxLength >= 0 && value.length > field.maxLength) {
            return 'Tối đa ' + field.maxLength + ' ký tự.';
        }
        if (field.minLength >= 0 && value && value.length < field.minLength) {
            return 'Vui lòng nhập ít nhất ' + field.minLength + ' ký tự.';
        }
        if (field.validity.typeMismatch) {
            return 'Địa chỉ email không đúng định dạng.';
        }
        if (field.validity.patternMismatch) {
            return 'Mã OTP phải gồm đúng 6 chữ số.';
        }
        if ('phone' in rules && value.trim()
                && (!/^\+?[0-9][0-9 .()-]*$/.test(value)
                    || !/^[0-9]{7,15}$/.test(value.replace(/[^0-9]/g, '')))) {
            return 'Số điện thoại cần có 7–15 chữ số.';
        }
        if (rules.match && value !== form.elements.namedItem(rules.match).value) {
            return 'Mật khẩu xác nhận không khớp.';
        }
        if (rules.textBytes) {
            const submittedText = value.replace(/\r\n|\r|\n/g, '\r\n');
            if (new TextEncoder().encode(submittedText).length > Number(rules.textBytes)) {
                return 'Mô tả tối đa 65.535 byte UTF-8.';
            }
        }

        const file = field.files?.[0];
        if (file) {
            if (!file.size) {
                return 'Tệp đã chọn không có nội dung.';
            }
            if (rules.maxBytes && file.size > Number(rules.maxBytes)) {
                return 'Tệp không được vượt quá ' + (Number(rules.maxBytes) / 1048576) + ' MB.';
            }
            const accepted = field.accept.toLowerCase().split(',');
            const filename = file.name.toLowerCase();
            if (field.accept && !accepted.some(type => type.startsWith('.')
                ? filename.endsWith(type)
                : type === file.type.toLowerCase())) {
                return rules.fileMessage;
            }
        }

        return field.validity.valid ? '' : 'Giá trị đã nhập không hợp lệ.';
    }

    function showFeedback(field, message) {
        const id = field.id + '-feedback';
        let feedback = document.getElementById(id);

        if (message && !feedback) {
            feedback = document.createElement('div');
            feedback.id = id;
            feedback.className = 'invalid-feedback';
            field.after(feedback);
            const describedBy = field.getAttribute('aria-describedby');
            field.setAttribute('aria-describedby', describedBy ? describedBy + ' ' + id : id);
        }

        if (feedback) {
            feedback.textContent = message;
            feedback.hidden = !message;
        }
        field.classList.toggle('is-invalid', Boolean(message));
        if (message) {
            field.setAttribute('aria-invalid', 'true');
        } else {
            field.removeAttribute('aria-invalid');
        }
    }

    function validateForm() {
        let firstInvalid;
        for (const field of fields) {
            const message = validateField(field);
            showFeedback(field, message);
            if (message && !firstInvalid) {
                firstInvalid = field;
            }
        }
        return firstInvalid;
    }

    form.addEventListener('submit', event => {
        attempted = true;
        const firstInvalid = validateForm();
        if (firstInvalid) {
            event.preventDefault();
            firstInvalid.focus();
        }
    });

    for (const event of ['input', 'change']) {
        form.addEventListener(event, () => {
            if (attempted) {
                validateForm();
            }
        });
    }

    form.addEventListener('reset', () => {
        attempted = false;
        fields.forEach(field => showFeedback(field, ''));
    });

    form.noValidate = true;
}

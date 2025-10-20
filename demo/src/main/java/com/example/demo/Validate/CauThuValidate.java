package com.example.demo.Validate;

import com.example.demo.dto.CauThuDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

public class CauThuValidate implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CauThuDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CauThuDTO cauThuDTO = (CauThuDTO) target;

        if (cauThuDTO.getName() == null || cauThuDTO.getName().trim().isEmpty()) {
            errors.rejectValue("name", "notEmpty", "Tên cầu thủ không được để trống");
        } else if (!cauThuDTO.getName().matches("^[A-Z][a-zA-ZÀ-ỹ\\s]{1,49}$")) {
            errors.rejectValue("name", "name.pattern", "Tên phải bắt đầu bằng chữ hoa và chỉ chứa chữ cái, khoảng trắng");
        }

        if (cauThuDTO.getBirthday() == null) {
            errors.rejectValue("birthday", "notEmpty", "Ngày sinh không được để trống");
        } else if (cauThuDTO.getBirthday().isAfter(LocalDate.now())) {
            errors.rejectValue("birthday", "invalidDate", "Ngày sinh không hợp lệ (lớn hơn hiện tại)");
        }


        if (cauThuDTO.getExprience() == null || cauThuDTO.getExprience().trim().isEmpty()) {
            errors.rejectValue("exprience", "notEmpty", "Kinh nghiệm không được để trống");
        } else if (cauThuDTO.getExprience().length() > 255) {
            errors.rejectValue("exprience", "length", "Kinh nghiệm quá dài (tối đa 255 ký tự)");
        }


        if (cauThuDTO.getLocation() == null || cauThuDTO.getLocation().trim().isEmpty()) {
            errors.rejectValue("location", "notEmpty", "Vị trí không được để trống");
        }


        if (cauThuDTO.getImage() == null || cauThuDTO.getImage().trim().isEmpty()) {
            errors.rejectValue("image", "notEmpty", "Phải chọn hình ảnh cho cầu thủ");
        }

        if (cauThuDTO.getDoiTuyen() == null) {
            errors.rejectValue("doiTuyen", "notNull", "Phải chọn đội tuyển");
        }
    }
}

package com.example.demo.service;


import com.example.demo.entity.CauThu;
import com.example.demo.repository.ICauThuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CauThuService implements ICauThuService {
 @Autowired
 private ICauThuRepository cauThuRepository;

    @Override
    public List<CauThu> findAll() {
        return cauThuRepository.findAll();
    }

    @Override
    public CauThu findCauThuByCauId(int cauId) {
        return cauThuRepository.findById(cauId).get();
    }

    @Override
    public CauThu addCauThu(CauThu cauThu) {
        return cauThuRepository.save(cauThu);
    }

    @Override
        public CauThu updateCauThu(CauThu cauThu) {
        CauThu existing = cauThuRepository.findById(cauThu.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cầu thủ có id = " + cauThu.getId()));

        existing.setName(cauThu.getName());
        existing.setBirthday(cauThu.getBirthday());
        existing.setLocation(cauThu.getLocation());
        existing.setExprience(cauThu.getExprience());
        existing.setImage(cauThu.getImage());

        return cauThuRepository.save(existing);
    }

    @Override
    public void deleteCauThu(int cauId) {
        if (cauThuRepository.existsById(cauId)) {
            cauThuRepository.deleteById(cauId);
        } else {
            throw new RuntimeException("Không tìm thấy cầu thủ có id = " + cauId);
        }

    }


    @Override
    public Page<CauThu> findAllCauThu(String name, Pageable pageable) {
        if (name == null || name.trim().isEmpty()) {
            return cauThuRepository.findAll(pageable);
        }
        return cauThuRepository.findAllByNameContaining(name, pageable);
    }

    @Override
    public Page<CauThu> findAll(Pageable pageable) {
        return cauThuRepository.findAll(pageable);
    }
}

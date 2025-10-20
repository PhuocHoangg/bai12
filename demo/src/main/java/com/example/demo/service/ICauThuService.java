package com.example.demo.service;




import com.example.demo.entity.CauThu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICauThuService {
    List<CauThu> findAll();
    CauThu findCauThuByCauId(int cauId);
    CauThu addCauThu(CauThu cauThu);
    CauThu updateCauThu(CauThu cauThu);
    void deleteCauThu(int cauId);
    Page<CauThu> findAllCauThu(String name,Pageable pageable);
    Page<CauThu> findAll(Pageable pageable);
}

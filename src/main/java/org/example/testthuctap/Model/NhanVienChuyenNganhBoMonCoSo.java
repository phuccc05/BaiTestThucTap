package org.example.testthuctap.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "staff_major_facility")
public class NhanVienChuyenNganhBoMonCoSo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_staff")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "id_major_facility")
    private ChuyenNganhBoMonCoSo chuyenNganhBoMonCoSo;

    @Column(name = "status")
    private Integer trangThai;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Long ngayTao;

    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Long ngaySua;

    public String getNgayTaoFormatted() {
        if (ngayTao != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.format(new Date(ngayTao));
        }
        return "";
    }

    public String getNgaySuaFormatted() {
        if (ngaySua != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.format(new Date(ngaySua));
        }
        return "";
    }
}

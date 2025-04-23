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
@Table(name = "staff")
public class NhanVien {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "account_fe")
    private String taiKhoanFE;

    @Column(name = "account_fpt")
    private String taiKhoanFPT;

    @Column(name = "name")
    private String ten;

    @Column(name = "staff_code")
    private String maNhanVien;

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
//    @ManyToOne
//    @JoinColumn(name = "chuyen_nganh_id")
//    private ChuyenNganh chuyenNganh;
}

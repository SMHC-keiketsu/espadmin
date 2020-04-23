package me.smhc.modules.cts.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
* @author jhf
* @date 2020-04-23
*/
@Entity
@Data
@Table(name="yunyu")
public class Yunyu implements Serializable {

    @Id
    @NotNull(groups = ManifestHawb.Update.class)
    @Column(name = "YUNYU_CD")
    private String yunyuCd;

    @Column(name = "HOUJIN_NO")
    private String houjinNo;

    @Column(name = "YUNYU_NM_E")
    private String yunyuNmE;

    @Column(name = "YUNYU_YUUBIN_NO")
    private String yunyuYuubinNo;

    @Column(name = "YUNYU_ADD_E_1")
    private String yunyuAddE1;

    @Column(name = "YUNYU_ADD_E_2")
    private String yunyuAddE2;

    @Column(name = "YUNYU_ADD_E_3")
    private String yunyuAddE3;

    @Column(name = "YUNYU_ADD_E_4")
    private String yunyuAddE4;

    @Column(name = "YUNYU_NM_W")
    private String yunyuNmW;

    @Column(name = "YUNYU_ADD_W")
    private String yunyuAddW;

    @Column(name = "YUNYU_TEL")
    private String yunyuTel;

    @Column(name = "YUNYU_FAX")
    private String yunyuFax;

    @Column(name = "KENSAKU_TEL")
    private String kensakuTel;

    @Column(name = "NOUKIGEN_ENCYO_CD")
    private String noukigenEncyoCd;

    @Column(name = "NOUFU_HOUHOU")
    private String noufuHouhou;

    @Column(name = "KOUZA_NO")
    private String kouzaNo;

    @Column(name = "TANPO_NO")
    private String tanpoNo;

    public @interface Update {}

    public void copy(Yunyu source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

package org.example.meteorites.microscope.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.example.meteorites.microscope.web.rest.TestUtil;

public class MicroscopePictureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MicroscopePicture.class);
        MicroscopePicture microscopePicture1 = new MicroscopePicture();
        microscopePicture1.setId(1L);
        MicroscopePicture microscopePicture2 = new MicroscopePicture();
        microscopePicture2.setId(microscopePicture1.getId());
        assertThat(microscopePicture1).isEqualTo(microscopePicture2);
        microscopePicture2.setId(2L);
        assertThat(microscopePicture1).isNotEqualTo(microscopePicture2);
        microscopePicture1.setId(null);
        assertThat(microscopePicture1).isNotEqualTo(microscopePicture2);
    }
}

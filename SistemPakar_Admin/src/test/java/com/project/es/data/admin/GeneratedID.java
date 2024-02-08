package com.project.es.data.admin;

import com.project.es.data.libary.Util.GeneratedId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GeneratedID {

    @Test
    void testGeneratedID() {
        String id = "PS165";
        String s = GeneratedId.generatedIdPasiens(id);
        Assertions.assertEquals("PS166",s);
    }
}

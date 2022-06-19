module ru.itmo.common {
    requires com.google.gson;
    requires org.apache.poi.ooxml;
    exports ru.itmo.common.model;
    exports ru.itmo.common.requests;
    exports ru.itmo.common.responses;
    exports ru.itmo.common.general;
}

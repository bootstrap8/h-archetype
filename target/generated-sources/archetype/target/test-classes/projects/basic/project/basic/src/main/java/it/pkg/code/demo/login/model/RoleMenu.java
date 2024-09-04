package it.pkg.code.demo.login.model;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class RoleMenu {
    private List<Map> all;
    private Set<Long> conf;
}

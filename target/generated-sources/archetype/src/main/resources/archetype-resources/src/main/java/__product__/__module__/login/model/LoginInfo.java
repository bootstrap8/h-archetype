#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${product}.${module}.login.model;

import lombok.Data;

@Data
public class LoginInfo {
    private String username;
    private String password;
}

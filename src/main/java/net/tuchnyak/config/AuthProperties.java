package net.tuchnyak.config;

import rife.ioc.HierarchicalProperties;

/**
 * @author tuchnyak (George Shchennikov)
 */
public record AuthProperties(
        String schema,
        String sequenceAuthRole,
        String tableUser,
        String tableRole,
        String tableUserRole,
        String tableAuthentication,
        String tableRemember
) {

    public static final String DOT = ".";

    public AuthProperties(HierarchicalProperties properties) {
        this(
                properties.getValueString("auth.schema"),
                properties.getValueString("auth.schema") + DOT + properties.getValueString("auth.sequence.auth_role"),
                properties.getValueString("auth.schema") + DOT + properties.getValueString("auth.table.user"),
                properties.getValueString("auth.schema") + DOT + properties.getValueString("auth.table.role"),
                properties.getValueString("auth.schema") + DOT + properties.getValueString("auth.table.user_role"),
                properties.getValueString("auth.schema") + DOT + properties.getValueString("auth.table.authentication"),
                properties.getValueString("auth.schema") + DOT + properties.getValueString("auth.table.remember")
        );
    }
}

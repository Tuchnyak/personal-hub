package net.tuchnyak.config;

import rife.ioc.HierarchicalProperties;

/**
 * @author tuchnyak (George Shchennikov)
 */
public record AppConfigurations(
        AuthProperties authProperties
) {
    public AppConfigurations(HierarchicalProperties properties) {
        this(
                new AuthProperties(properties)
        );
    }
}

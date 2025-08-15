package net.tuchnyak.util;

import rife.template.Template;

/**
 * @author tuchnyak (George Shchennikov)
 */
public class BlockAppendHandler {

    private final Template template;
    private int count = 0;

    public BlockAppendHandler(Template template) {
        this.template = template;
    }

    public void setOrAppend(String name) {
        setOrAppend(name, name);
    }

    public void setOrAppend(String contentVarName, String blockName) {
        if (count == 0) {
            template.setBlock(contentVarName, blockName);
            count++;
        } else {
            template.appendBlock(contentVarName, blockName);
        }
    }

}

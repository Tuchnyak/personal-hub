package net.tuchnyak.db;

import rife.database.DbQueryManager;

/**
 * @author tuchnyak (George Shchennikov)
 */
public interface Transactional {

    DbQueryManager getDbQueryManager();

}

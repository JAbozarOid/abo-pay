package com.jabozaroid.abopay.core.database.util

/**
 * Created on 31,July,2024
 */

import androidx.room.DeleteColumn
import androidx.room.DeleteTable
import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec

/**
 * Automatic schema migrations sometimes require extra instructions to perform the migration, for
 * example, when a column is renamed. These extra instructions are placed here by creating a class
 * using the following naming convention `SchemaXtoY` where X is the schema version you're migrating
 * from and Y is the schema version you're migrating to. The class should implement
 * `AutoMigrationSpec`.
 */
internal object DatabaseMigrations {

    //TODO: This migration should be changed according to the requirements and it is only as a sample
    @RenameColumn(
        tableName = "users",
        fromColumnName = "name",
        toColumnName = "name2",
    )
    @DeleteColumn(
        tableName = "users",
        columnName = "lastname",
    )
    @DeleteTable.Entries(
        DeleteTable(
            tableName = "users",
        ),
    )
    class Schema1to2 : AutoMigrationSpec

}

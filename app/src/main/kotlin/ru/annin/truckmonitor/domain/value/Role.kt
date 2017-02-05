package ru.annin.truckmonitor.domain.value

/**
 * Роль пользователя.
 *
 * @author Pavel Annin.
 */
enum class Role(val role: String) {

    /** Администратор. */
    ADMIN("admin"),

    /** Заказчик. */
    CLIENT("client"),

    /** Водитель. */
    DRIVER("driver"),

    /** Менеджер. */
    MANAGER("manager");

    companion object {
        @JvmStatic
        fun findByRole(role: String): Role? = values().find { it.role == role }
    }
}
package lesson12.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PhoneBookTest {

    @Test
    fun addHuman() {
        val book = PhoneBook()
        assertTrue(book.addHuman("Иванов Петр"))
        assertTrue(book.addHuman("Васильев Дмитрий"))
        assertFalse(book.addHuman("Иванов Петр"))
        assertFalse(book.addHuman("Васильев Дмитрий"))
    }

    @Test
    fun removeHuman() {
        val book = PhoneBook()
        assertTrue(book.addHuman("Иванов Петр"))
        assertTrue(book.addHuman("Васильев Дмитрий"))
        assertTrue(book.removeHuman("Иванов Петр"))
        assertFalse(book.removeHuman("Сидорова Мария"))
        assertFalse(book.removeHuman("Кирилл"))
        assertFalse(book.removeHuman(""))
    }

    @Test
    fun addPhone() {
        val book = PhoneBook()
        assertTrue(book.addHuman("Иванов Петр"))
        assertTrue(book.addHuman("Васильев Дмитрий"))
        assertTrue(book.addPhone("Иванов Петр", "+79211234567"))
        assertTrue(book.addPhone("Иванов Петр", "+78121234567"))
        assertFalse(book.addPhone("Иванов Петр", "+79211234567"))
        assertFalse(book.addPhone("Васильев Дмитрий", "+79211234567"))
        assertTrue(book.addPhone("Васильев Дмитрий", "+79217654321"))
        assertTrue(book.addHuman("Петров Иван"))
        assertTrue(book.addPhone("Петров Иван", "+78221244561"))
        assertTrue(book.addPhone("Петров Иван", "+79222124422"))
        assertFalse(book.addPhone("Петров Иван", "+78221244561"))
    }

    @Test
    fun removePhone() {
        val book = PhoneBook()
        assertTrue(book.addHuman("Иванов Петр"))
        assertTrue(book.addHuman("Васильев Дмитрий"))
        assertTrue(book.addPhone("Иванов Петр", "+79211234567"))
        assertTrue(book.addPhone("Иванов Петр", "+78121234567"))
        assertTrue(book.addPhone("Васильев Дмитрий", "+79217654321"))
        assertTrue(book.removePhone("Иванов Петр", "+78121234567"))
        assertFalse(book.removePhone("Иванов Петр", "+78121234567"))
        assertTrue(book.removePhone("Васильев Дмитрий", "+79217654321"))
        assertFalse(book.removePhone("", "+78121234567"))
        assertFalse(book.removePhone("Васильев Дмитрий", "+79211554312"))
    }

    @Test
    fun phones() {
        val book = PhoneBook()
        assertTrue(book.addHuman("Иванов Петр"))
        assertTrue(book.addHuman("Васильев Дмитрий"))
        assertTrue(book.addPhone("Иванов Петр", "+79211234567"))
        assertTrue(book.addPhone("Иванов Петр", "+78121234567"))
        assertEquals(setOf("+79211234567", "+78121234567"), book.phones("Иванов Петр"))
        assertEquals(emptySet<String>(), book.phones(""))
    }

    @Test
    fun humanByPhone() {
        val book = PhoneBook()
        assertTrue(book.addHuman("Иванов Петр"))
        assertTrue(book.addHuman("Васильев Дмитрий"))
        assertTrue(book.addPhone("Иванов Петр", "+79211234567"))
        assertTrue(book.addPhone("Иванов Петр", "+78121234567"))
        assertTrue(book.addPhone("Васильев Дмитрий", "+79217654321"))
        assertEquals("Васильев Дмитрий", book.humanByPhone("+79217654321"))
        assertEquals("Иванов Петр", book.humanByPhone("+78121234567"))
        assertNull(book.humanByPhone("+78127654321"))
        assertNull(book.humanByPhone("+79212762221"))
    }

    @Test
    fun testEquals() {
        val book = PhoneBook()
        assertTrue(book.addHuman("Иванов Петр"))
        assertTrue(book.addHuman("Васильев Дмитрий"))
        assertTrue(book.addPhone("Иванов Петр", "+79211234567"))
        assertTrue(book.addPhone("Иванов Петр", "+78121234567"))
        assertTrue(book.addPhone("Васильев Дмитрий", "+79217654321"))
        val book2 = PhoneBook()
        assertTrue(book2.addHuman("Васильев Дмитрий"))
        assertTrue(book2.addHuman("Иванов Петр"))
        assertTrue(book2.addPhone("Иванов Петр", "+78121234567"))
        assertTrue(book2.addPhone("Васильев Дмитрий", "+79217654321"))
        assertTrue(book2.addPhone("Иванов Петр", "+79211234567"))
        assertTrue(book == book2)
        val book3 = PhoneBook()
        assertTrue(book3.addHuman("Иванов Петр"))
        assertTrue(book3.addPhone("Иванов Петр", "+79211234567"))
        assertTrue(book3.addPhone("Иванов Петр", "+78121234567"))
        val book4 = PhoneBook()
        assertTrue(book4.addHuman("Васильев Дмитрий"))
        assertTrue(book4.addPhone("Васильев Дмитрий", "+79217654321"))
        assertFalse(book3 == book4)
        val book5 = PhoneBook()
        assertTrue(book5.addHuman("Иванов Петр"))
        assertTrue(book5.addHuman("Васильев Дмитрий"))
        assertTrue(book5.addPhone("Иванов Петр", "+79211234567"))
        assertTrue(book5.addPhone("Иванов Петр", "+78121234567"))
        assertTrue(book5.addPhone("Васильев Дмитрий", "+79217654321"))
        assertTrue(book5.addPhone("Васильев Дмитрий", "+7905765333"))
        val book6 = PhoneBook()
        assertTrue(book6.addHuman("Васильев Дмитрий"))
        assertTrue(book6.addHuman("Иванов Петр"))
        assertTrue(book6.addPhone("Иванов Петр", "+78121234567"))
        assertTrue(book6.addPhone("Васильев Дмитрий", "+79217654321"))
        assertTrue(book6.addPhone("Иванов Петр", "+79211234567"))
        assertFalse(book5 == book6)
    }

    @Test
    fun testHashCode() {
        val book = PhoneBook()
        assertTrue(book.addHuman("Иванов Петр"))
        assertTrue(book.addHuman("Васильев Дмитрий"))
        assertTrue(book.addPhone("Иванов Петр", "+79211234567"))
        assertTrue(book.addPhone("Иванов Петр", "+78121234567"))
        assertTrue(book.addPhone("Васильев Дмитрий", "+79217654321"))
        val book2 = PhoneBook()
        assertTrue(book2.addHuman("Васильев Дмитрий"))
        assertTrue(book2.addHuman("Иванов Петр"))
        assertTrue(book2.addPhone("Иванов Петр", "+78121234567"))
        assertTrue(book2.addPhone("Васильев Дмитрий", "+79217654321"))
        assertTrue(book2.addPhone("Иванов Петр", "+79211234567"))
        assertTrue(book.hashCode() == book2.hashCode())
    }
}
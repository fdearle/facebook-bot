/*
 * This Spock specification was auto generated by running the Gradle 'init' task
 * by 'fdearle' at '19/04/16 09:30' with Gradle 2.12
 *
 * @author fdearle, @date 19/04/16 09:30
 */

import spock.lang.Specification

class LibraryTest extends Specification{
    def "someLibraryMethod returns true"() {
        setup:
        Library lib = new Library()
        when:
        def result = lib.someLibraryMethod()
        then:
        result == true
    }
}
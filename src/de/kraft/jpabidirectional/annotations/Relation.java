package de.kraft.jpabidirectional.annotations;

import de.kraft.jpabidirectional.enums.RelationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * describes a relationship between objects
 * @author Alexander Kraft
 * @version 1.0
 * @since 04.08.2017
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Relation {
    /**
     * @return return the name of the opposite property
     */
    String mappedBy();

    /**
     * @return return the type of relationship
     */
    RelationType type();
}

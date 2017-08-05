package de.kraft.jpabidirectional.base;

import de.kraft.jpabidirectional.annotations.Relation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides simple methods for creating bidirectional relation
 * @author Alexander Kraft
 * @version 1.0
 * @since 04.08.2017
 *
 */
public class BidirectionalBase {


    /**
     * this method set the bidirectional relation. For non-list types set null to remove
     * @param fieldName Name of the field from the derived class, which is the bidirectional property
     * @param param paramter to be set
     * @param <T> type of parameter
     */
    protected <T extends BidirectionalBase> void setEntity(String fieldName, T param) {

        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            Relation relation = field.getAnnotation(Relation.class);
            field.setAccessible(true);

            switch (relation.type()) {
                case OneToOne: internalSetOneToOne(param, field, relation); break;
                case OneToMany: internalSetOneToMany(param, field, relation); break;
                case ManyToOne: internalSetManyToOne(param, field, relation); break;
                case ManyToMany: internalSetManyToMany(param, field, relation); break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * this method removes the bidirectional relation. Is only required for list types
     * @param fieldName Name of the field from the derived class, which is the bidirectional property
     * @param param paramter to be set
     * @param <T> type of parameter
     */
    protected <T extends BidirectionalBase> void unsetEntity(String fieldName, T param) {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            Relation relation = field.getAnnotation(Relation.class);
            field.setAccessible(true);

            switch (relation.type()) {
                case OneToOne: internalUnsetOneToOne(param, field, relation); break;
                case OneToMany: internalUnsetOneToMany(param, field, relation); break;
                case ManyToOne: internalUnsetManyToOne(param, field, relation); break;
                case ManyToMany: internalUnsetManyToMany(param, field, relation); break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * set internal the OneToOne Relation
     * @param param paramter to be set
     * @param field the field from the derived class, which is the bidirectional property
     * @param relation Instance of relation annotation
     * @param <T> type of the parameter
     */
    private <T extends BidirectionalBase> void internalSetOneToOne(T param, Field field, Relation relation) {

        try {
            if (field.get(this) != null) ((BidirectionalBase)field.get(this)).internalSet(null, relation.mappedBy());

            field.set(this, param);

            if (param != null && field.get(this) != null) ((BidirectionalBase)field.get(this)).internalSet(this, relation.mappedBy());

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    /**
     * unset internal the OneToOne Relation
     * @param param paramter to be set
     * @param field the field from the derived class, which is the bidirectional property
     * @param relation Instance of relation annotation
     * @param <T> type of the parameter
     */
    private <T extends BidirectionalBase> void internalUnsetOneToOne(T param, Field field, Relation relation) {
        internalSetOneToOne(param, field, relation);
    }

    /**
     * set internal the OneToMany Relation
     * @param param paramter to be set
     * @param field the field from the derived class, which is the bidirectional property
     * @param relation Instance of relation annotation
     * @param <T> type of the parameter
     */
    private <T extends BidirectionalBase> void internalSetOneToMany(T param, Field field, Relation relation) {
        try {

            if (field.get(this) != null) ((BidirectionalBase) field.get(this)).internalRemove(this, relation.mappedBy());

            field.set(this, param);

            if (param != null) ((BidirectionalBase) field.get(this)).internalAdd(this, relation.mappedBy());

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * unset internal the OneToMany Relation
     * @param param paramter to be set
     * @param field the field from the derived class, which is the bidirectional property
     * @param relation Instance of relation annotation
     * @param <T> type of the parameter
     */
    private <T extends BidirectionalBase> void internalUnsetOneToMany(T param, Field field, Relation relation) {
        internalSetOneToMany(param, field, relation);
    }

    /**
     * set internal the ManyToOne Relation
     * @param param paramter to be set
     * @param field the field from the derived class, which is the bidirectional property
     * @param relation Instance of relation annotation
     * @param <T> type of the parameter
     */
    private <T extends BidirectionalBase> void internalSetManyToOne(T param, Field field, Relation relation) {
        try {

            ((BidirectionalBase)param).internalSet(this, relation.mappedBy());
            this.internalAdd(param, field.getName());

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * unset internal the ManyToOne Relation
     * @param param paramter to be set
     * @param field the field from the derived class, which is the bidirectional property
     * @param relation Instance of relation annotation
     * @param <T> type of the parameter
     */
    private <T extends BidirectionalBase> void internalUnsetManyToOne(T param, Field field, Relation relation) {
        try {

            ((BidirectionalBase)param).internalSet(null, relation.mappedBy());
            this.internalRemove(param, field.getName());

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * set internal the ManyToMany Relation
     * @param param paramter to be set
     * @param field the field from the derived class, which is the bidirectional property
     * @param relation Instance of relation annotation
     * @param <T> type of the parameter
     */
    private <T extends BidirectionalBase> void internalSetManyToMany(T param, Field field, Relation relation) {
        try {

            ((BidirectionalBase)param).internalAdd(this, relation.mappedBy());
            this.internalAdd(param, field.getName());

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * unset internal the ManyToMany Relation
     * @param param paramter to be set
     * @param field the field from the derived class, which is the bidirectional property
     * @param relation Instance of relation annotation
     * @param <T> type of the parameter
     */
    private <T extends BidirectionalBase> void internalUnsetManyToMany(T param, Field field, Relation relation) {
        try {

            ((BidirectionalBase)param).internalRemove(this, relation.mappedBy());
            this.internalRemove(param, field.getName());

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Contains the logic for setting and removing the relation on the other side for non-list types
     * @param param paramter to be set
     * @param <T> type of the parameter
     */
    private <T extends BidirectionalBase> void internalSet(T param, String propertyName) {

        try {
            Field field = this.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);

            field.set(this, param);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Contains the logic for setting the relation on the other side for list types
     * @param param paramter to be set
     * @param <T> type of the parameter
     */
    private <T extends BidirectionalBase> void internalAdd(T param, String propertyName) {

        try {
            Field field = this.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);

            if (field.get(this) == null)
                field.set(this, new ArrayList<T>());

            ((List<T>) field.get(this)).add(param);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Contains the logic for removing the relation on the other side for list types
     * @param param paramter to be set
     * @param <T> type of the parameter
     */
    private <T extends BidirectionalBase> void internalRemove(T param, String propertyName) {

        try {
            Field field = this.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);

            if (field.get(this) == null)
                field.set(this, new ArrayList<T>());

            ((List<T>) field.get(this)).remove(param);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

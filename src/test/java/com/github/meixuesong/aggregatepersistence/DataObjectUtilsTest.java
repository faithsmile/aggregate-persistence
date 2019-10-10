package com.github.meixuesong.aggregatepersistence;

import lombok.EqualsAndHashCode;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DataObjectUtilsTest {
    @Test
    public void should_get_delta_object() {
        //Given
        Date birthday = new Date();
        String id = "ID";
        int length = 100;
        String money = "100.00";

        Date newBirthday = new Date(birthday.getTime() + 1000);
        String newId = "ID2";
        int newLength = 200;

        SampleEntity entity1 = new SampleEntity(birthday, id, length, new BigDecimal(money), Arrays.asList(new SampleEntity()));
        SampleEntity entity2 = new SampleEntity(newBirthday, newId, newLength, new BigDecimal(money), Arrays.asList(new SampleEntity()));

        //When
        SampleEntity actualDelta = new DataObjectUtils().getDelta(entity1, entity2);

        //Then
        SampleEntity expectedDelta = new SampleEntity(newBirthday, newId, newLength, null, null);

        assertEquals(expectedDelta, actualDelta);
    }

    @Test
    public void should_get_delta_when_children_changed() {
        //Given
        Date birthday = new Date();
        String id = "ID";
        int length = 100;
        String money = "100.00";

        SampleEntity entity1 = new SampleEntity(birthday, id, length, new BigDecimal(money), Arrays.asList(new SampleEntity()));
        SampleEntity entity2 = new SampleEntity(birthday, id, length, new BigDecimal(money), Arrays.asList(new SampleEntity(), new SampleEntity()));

        //When
        SampleEntity actualDelta = new DataObjectUtils().getDelta(entity1, entity2);

        //Then
        SampleEntity expectedDelta = new SampleEntity(null, null, null, null, Arrays.asList(new SampleEntity(), new SampleEntity()));

        assertEquals(expectedDelta, actualDelta);
    }

    @Test
    public void should_ignore_specify_field() {
        //Given
        Date birthday = new Date();
        String id = "ID";
        int length = 100;
        String money = "100.00";

        SampleEntity entity1 = new SampleEntity(birthday, id, length, new BigDecimal(money), Arrays.asList(new SampleEntity()));
        SampleEntity entity2 = new SampleEntity(birthday, id, length, new BigDecimal(money), Arrays.asList(new SampleEntity(), new SampleEntity()));

        //When
        SampleEntity actualDelta = new DataObjectUtils().getDelta(entity1, entity2, "children");

        //Then
        SampleEntity expectedDelta = new SampleEntity(null, null, null, null, null);

        assertEquals(expectedDelta, actualDelta);
    }

    @EqualsAndHashCode
    static class SampleEntity {
        private String id;
        private boolean checked;
        private int age;
        private Integer length;
        private double area;
        private Double area2;
        private BigDecimal money;
        private Date birthday;
        private LocalDate meetingTime;
        private List<SampleEntity> children;

        public SampleEntity() {
        }

        public SampleEntity(Date newBirthday, String newId, Integer newLength, BigDecimal money, List<SampleEntity> children){
            birthday = newBirthday;
            id = newId;
            length = newLength;
            this.money = money;
            this.children = children;
        }
    }

}
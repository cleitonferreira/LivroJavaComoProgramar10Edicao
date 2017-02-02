package com.deitel.jhtp.jpa;

import com.deitel.jhtp.jpa.Titles;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-13T09:30:34")
@StaticMetamodel(Authors.class)
public class Authors_ { 

    public static volatile SingularAttribute<Authors, String> firstname;
    public static volatile SingularAttribute<Authors, Integer> authorid;
    public static volatile ListAttribute<Authors, Titles> titlesList;
    public static volatile SingularAttribute<Authors, String> lastname;

}
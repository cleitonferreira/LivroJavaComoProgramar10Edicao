package com.deitel.jhtp.jpa;

import com.deitel.jhtp.jpa.Authors;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-08-13T09:30:34")
@StaticMetamodel(Titles.class)
public class Titles_ { 

    public static volatile SingularAttribute<Titles, Integer> editionnumber;
    public static volatile SingularAttribute<Titles, String> copyright;
    public static volatile SingularAttribute<Titles, String> isbn;
    public static volatile SingularAttribute<Titles, String> title;
    public static volatile ListAttribute<Titles, Authors> authorsList;

}
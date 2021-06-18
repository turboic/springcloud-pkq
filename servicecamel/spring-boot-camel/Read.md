you are using spring-boot-maven-plugin:2.1.6.RELEASE.

since Spring-Boot 2 you don't need the spring boot plugin anymore.

you can use the following code after declaring the artifact id of your module.

<artifactId>pet-clinic-data</artifactId>
<properties>
    <spring-boot.repackage.skip>true</spring-boot.repackage.skip>
</properties>
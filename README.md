# Java EE 6 sample project

Successfully tested against jboss-as-7.1.0.CR1b

Add this to your settings.xml

    <profile>
    <id>jboss-public-repository</id>
    <activation>
        <property>
            <name>jboss-public-repository</name>
            <value>!false</value>
        </property>
    </activation>
    <repositories>
        <repository>
            <id>jboss-public-repository-group</id>
            <name>JBoss Public Maven Repository Group</name>
            <url>http://repository.jboss.org/nexus/content/groups/public</url>
            <releases>
                <enabled>true</enabled>
                <updatePolicy>never</updatePolicy>
            </releases>
            <snapshots>
                <enabled>false</enabled>
                <updatePolicy>never</updatePolicy>
            </snapshots>
        </repository>
    </repositories>
    <pluginRepositories>
        <pluginRepository>
            <id>jboss-public-repository-group</id>
            <name>JBoss Public Maven Repository Group</name>
            <url>http://repository.jboss.org/nexus/content/groups/public</url>
            <releases>
                <enabled>true</enabled>
                <updatePolicy>never</updatePolicy>
            </releases>
            <snapshots>
                <enabled>false</enabled>
                <updatePolicy>never</updatePolicy>
            </snapshots>
        </pluginRepository>
    </pluginRepositories>
    </profile>

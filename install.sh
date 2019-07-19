#!/bin/sh
pr_path="$PWD/absence-control"
tomcat_dir='/opt'
. $PWD/install.conf
/usr/bin/tar -C $pr_path -xvzf $pr_path/tomcat.tar.gz
java -version| grep JDK | grep 1.8> /dev/null
if [ $? -ne 1 ]; then  /usr/bin/yum -y install java-1.8.0-openjdk-1.8*
fi

which mvn>/dev/null
if [ $? -ne 0 ]; then  /usr/bin/yum -y install maven-3*
fi

which psql>/dev/null
if [ $? -ne 0 ]; then  
{
    /usr/bin/yum install postgresql-server*
    /usr/bin/postgresql-setup initdb
    /usr/bin/sed -i 's%host.*all.*127.0.0.1.*%host all all 127\.0\.0\.1\/32 trust %' /var/lib/pgsql/data/pg_hba.conf
    /usr/bin/systemctl enable postgresql
    /usr/bin/systemctl start postgresql
}
fi
su postgres -c "
psql template1 <<END
CREATE USER $db_user;
ALTER ROLE $db_user WITH ENCRYPTED PASSWORD '$db_passd';
\q
END
createdb -O $db_user $db_name
exit
psql template1 <<END
GRANT ALL PRIVILEGES ON DATABASE absence TO absence;
\q
END
"

/usr/bin/sed -i 's@testdb@'"$db_name"'@' $pr_path/project/pom.xml
/usr/bin/sed -i 's@postgresuser@'"$db_user"'@' $pr_path/project/pom.xml
/usr/bin/sed -i 's@postgrespassword@'"$db_pwd"'@' $pr_path/project/pom.xml
cd $pr_path/project
/usr/bin/mvn clean install
/usr/bin/mkdir $tomcat_dir/tomcat
/usr/bin/cp -R $pr_path/tomcat/tomcat $tomcat_dir/
/usr/bin/cp $pr_path/tomcat/tomcat.service /etc/systemd/system
/usr/sbin/useradd -r tomcat -d $tomcat_dir/tomcat --shell /bin/false
/usr/bin/cp $pr_path/project/target/absence-control.war $tomcat_dir/tomcat/webapps/
/usr/bin/chown tomcat:tomcat -R $tomcat_dir/tomcat
/usr/sbin/iptables -I INPUT -p tcp --dport 8080 -j ACCEPT
/usr/sbin/iptables -I OUTPUT -p tcp --dport 8080 -j ACCEPT
/usr/bin/systemctl daemon-reload
/usr/bin/systemctl enable tomcat
/usr/bin/systemctl start tomcat

which python3>/dev/null
if [ $? -ne 0 ]; then 
{
/usr/bin/yum -y install epel-release
/usr/bin/yum -y install python36
}
$pr_path/tester.py
fi





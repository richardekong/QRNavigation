#!/usr/bin/bash


#INSTALLING THE QR NAVIGATION SYSTEM

echo "installing mariaDB ..."

sudo yum install mariadb-server -y
sudo systemctl start mariadb
sudo systemctl status mariadb
sudo systemctl enable mariadb

echo "creating mysql_secure_installation.tx ..."
touch mysql_secure_installation.txt

cat << `EOF` >> mysql_secure_installation.txt

n
Y
root
root
Y
Y
Y
Y
Y
`EOF`

echo "running mysql_secure_installation..."
sudo mysql_secure_installation < mysql_secure_installation.txt

cat << `EOF` >> gitlab_qrnavigation_deployment.key
-----BEGIN OPENSSH PRIVATE KEY-----
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
-----END OPENSSH PRIVATE KEY-----
`EOF`

chmod 400 gitlab_qrnavigation_deployment.key

touch ~/.ssh/known_hosts
ssh-keyscan git.cardiff.ac.uk >> ~/.ssh/known_hosts
chmod 644 ~/.ssh/known_hosts

sudo yum install git -y

echo "cloning into GitLab project"

ssh-agent bash -c 'ssh-add gitlab_qrnavigation_deployment.key; git clone git@git.cardiff.ac.uk:c22106964/qr-navigation.git'

cd qr-navigation

mysql -uroot -proot < src/BuildDB.sql

#Set up secret.properties file

echo "
spring.datasource.username=c22106964
spring.datasource.password=$db_password
s3.access.key.id=$s3_access_key_id
s3.access.key.secret=$s3_access_key_secret" > src/main/resources/secret.properties;

sudo yum install java-17-openjdk -y

#install wget
#use it to download the gradle 7.6 from the official gradle repository located at https://services.gradle.org/distributions/gradle-7.6-bin.zip
#install the unzip utility
#create a gradle directory in the opt/ directory, where it is recommended to store your tools
#use the unzip utility to unpack the gradle-7.6-bin.zip file to /opt/gradle directory

sudo yum install wget -y
wget https://services.gradle.org/distributions/gradle-7.6-bin.zip
sudo yum install unzip -y
sudo mkdir /opt/gradle
sudo unzip -d /opt/gradle gradle-7.6-bin.zip

#make rockylinux aware of the gradle directory by exporting the path to the environment variable
export PATH=$PATH:/opt/gradle/gradle-7.6/bin

#build the (QR Navigation) web application project

gradle clean

gradle build

#run the project
gradle bootRun

java -jar build/libs/QR-NavigationProject-0.0.1-SNAPSHOT.jar --server.port=8081

##!/usr/bin/bash
#cd /home/rocky || exit
#echo in directory "$PWD"
#
#echo "updating system package"
#sudo dnf update -y
#
## echo "instaling langauge package"
## sudo dnf install langpacks-en glibc-all-langpacks -y
#
#
#echo "installing MariaDB..."
## sudo yum install mysql -y
#sudo dnf install mariadb-server -y
#sudo systemctl start mariadb
#sudo systemctl status mariadb
#sudo systemctl enable mariadb
#
#
#echo "creating mysql_secure_installation.txt..."
#touch mysql_secure_installation.txt
#cat << `EOF` >> mysql_secure_installation.txt
#
#n
#Y
#root
#root
#Y
#Y
#Y
#Y
#Y
#`EOF`
#
#echo "running mysql_secure_installation..."
## shellcheck disable=SC2024
#sudo mysql_secure_installation < mysql_secure_installation.txt
#
## sudo yum install wget -y
#sudo yum install unzip -y
#sudo yum install git -y
#
#echo "Installing Java 17..."
#sudo yum install java-17-openjdk-devel -y
#echo java --version
#
#sudo yum install jenkins -y --nogpgcheck
#
#echo "install Jenkins"
#sudo rpm --import https://pkg.jenkins.io/redhat/jenkins.io-2023.key
## https://pkg.jenkins.io/redhat-stable/
#curl --silent --location http://pkg.jenkins-ci.org/redhat-stable/jenkins.repo | sudo tee /etc/yum.repos.d/jenkins.repo
#sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io.key
#sudo dnf install https://dl.fedoraproject.org/pub/epel/epel-release-latest-8.noarch.rpm -y
#sudo yum install jenkins -y
#sudo systemctl start jenkins
#
#
#echo "installing gitlab server key... has to be added to the jenkins user home (~) dir "
#mkdir /var/lib/jenkins/.ssh
#sudo touch /var/lib/jenkins/.ssh/known_hosts
#sudo ssh-keyscan git.cardiff.ac.uk >> /var/lib/jenkins/.ssh/known_hosts
#sudo chmod 644 /var/lib/jenkins/.ssh/known_hosts
#
#
## If you want jenkins on port 8081 so you can run your app on 8080 then change the default jenkins port.
##(look up linux sed - it is really cool)
## sudo sed -i 's/JENKINS_PORT="8080"/JENKINS_PORT="8081"/g' /etc/sysconfig/jenkins
#sudo systemctl start jenkins
#systemctl status jenkins
#sudo systemctl enable jenkins
#
#echo "Installing gradle..."
## wget https://services.gradle.org/distributions/gradle-6.7.1-bin.zip
#wget https://services.gradle.org/distributions/gradle-7.6-bin.zip
#sudo mkdir /opt/gradle
## sudo unzip -d /opt/gradle gradle-6.7.1-bin.zip
## export PATH=$PATH:/opt/gradle/gradle-6.7.1/bin
#sudo unzip -d /opt/gradle gradle-7.6-bin.zip
#export PATH=$PATH:/opt/gradle/gradle-7.6/bin
#echo gradle -v
#
##create secret.properties file
#
#echo "
#spring.datasource.username=c22106964
#spring.datasource.password=$db_password
#s3.access.key.id=$s3_access_key_id
#s3.access.key.secret=$s3_access_key_secret" > src/main/resources/secret.properties;
#
##build the (QR Navigation) web application project
#gradle build
#
##run the project
#gradle bootRun
#

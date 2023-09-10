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
#Add your private ssh key here
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

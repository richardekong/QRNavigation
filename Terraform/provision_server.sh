#!/usr/bin/env bash
#All good here
pwd
whoami
ls

export OS_AUTH_URL=https://cscloud.cf.ac.uk:5000
# With the addition of Keystone we have standardized on the term **project**
# as the entity that owns the resources.
export OS_PROJECT_ID=61323a6d9a9f47a08dd2200d2020d145
export OS_PROJECT_NAME="c22106964"
export OS_USER_DOMAIN_NAME="cardiff.ac.uk"
if [ -z "$OS_USER_DOMAIN_NAME" ]; then unset OS_USER_DOMAIN_NAME; fi
export OS_PROJECT_DOMAIN_ID="3693afdd0603423a9e8984fd32df7a0c"
if [ -z "$OS_PROJECT_DOMAIN_ID" ]; then unset OS_PROJECT_DOMAIN_ID; fi
# unset v2.0 items in case set
unset OS_TENANT_ID
unset OS_TENANT_NAME
# In addition to the owning entity (tenant), OpenStack stores the entity
# performing the action as the **user**.
export OS_USERNAME="c22106964"
# With Keystone you pass the keystone password.
echo "Please enter your OpenStack Password for project $OS_PROJECT_NAME as user $OS_USERNAME: "
#read -sr OS_PASSWORD_INPUT
#export OS_PASSWORD=$OS_PASSWORD_INPUT
export OS_PASSWORD=$unipass
# If your configuration has multiple regions, we set that information here.
# OS_REGION_NAME is optional and only valid in certain environments.
export OS_REGION_NAME="RegionOne"
# Don't leave a blank variable, unset it if it was empty
if [ -z "$OS_REGION_NAME" ]; then unset OS_REGION_NAME; fi
export OS_INTERFACE=public
export OS_IDENTITY_API_VERSION=3


if [ -d Terraform ]; then
echo "Destroying existing terraform directory"
cd Terraform
/usr/local/bin/terraform destroy -auto-approve
rm -rf Terraform
cd ../

fi

cp -r ../QRNAVIGATIONSYSTEM/Terraform ./

cd Terraform

echo 'Building second job'

/usr/local/bin/terraform init

/usr/local/bin/terraform plan

/usr/local/bin/terraform apply -auto-approve


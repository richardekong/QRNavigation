variable "flavor" { default = "m1.large" }
variable "image" { default = "Rocky Linux 9 20220830" }
variable "name1" { default = "QR Navigation Web Application Test version V2" }

variable "keypair" { default = "qrnav" } # you may need to change this
variable "network" { default = "qrnav" }   # you need to change this

variable "pool" { default = "cscloud_private_floating" }
variable "server1_script" { default = "./qrnavigation-web-app.sh"}
variable "security_description" { default = "QR Navigation Web Application security group" }
variable "security_name" { default = "tf_qrnavigation_sec_group_V2" }

#!/bin/bash

set -ex

terraform init -input=false
terraform plan -out=tfplan -input=false
terraform apply -input=false tfplan
ansible-playbook --key-file "./keys/infra_key" -i ./inventory/terraform.py site.yml

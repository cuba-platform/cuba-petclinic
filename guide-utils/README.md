### How to create an example for a new CUBA guide

In order to write a guide based on the CUBA petclinic the following steps should be taken:

1. `git clone git@github.com:cuba-platform/cuba-petclinic.git` to download the original example
2. `cp -R cuba-petclinic cuba-petclinic-my-example` to create a new guide example
3. create a repo in the cuba-guides github org: `cuba-petclinic-my-example`
4. `git remote set-url origin git@github.com:cuba-platform/cuba-petclinic-my-example.git`
5. `cd cuba-petclinic-my-example && ./guide-utils/add-upstream.sh` to point to the original upstream repo
6. `git remote -v` to view the git remote settings
7. do your changes for the example
8. occasionally update from the upstream petclinic example via `./guide-utils/update-from-upstream.sh`

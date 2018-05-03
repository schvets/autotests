for run test on local selenoid:
1) download config manager 
curl -L -o cm https://github.com/aerokube/cm/releases/download/1.2.3/cm_linux_amd64
2) change file permission
chmod +x ./cm
3) start selenoid session
./cm selenoid start --vnc
4) start selenoid-ui session
./cm selenoid-ui start
        
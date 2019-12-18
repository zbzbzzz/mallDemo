# mallDemo
一个基于开源项目litemall的学习项目


# 利用Alibaba Cloud Tookit加以下脚本可以在centerOS下一键部署项目


echo "脚本开始运行!"
service litemall stop
rm -f litemall.jar
mv litemall-all-0.1.0-exec.jar litemall.jar
ln -f -s ~/litemall.jar /etc/init.d/litemall
chmod a+x /etc/init.d/litemall
service litemall start
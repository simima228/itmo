mkdir lab0
cd lab0
touch darumaka0
touch growlithe8
touch krabby6
mkdir -p cherubi5/roselia
mkdir cherubi5/sealeo
mkdir cherubi5/armaldo
touch cherubi5/finneon
touch cherubi5/blitzle
mkdir -p gabite1/archeops
mkdir gabite1/piloswine
touch gabite1/umbreon
mkdir -p totodile5/lickilicky
mkdir totodile5/axew
touch totodile5/omastar
touch totodile5/zigzagoon
echo 'Живет  Ocean' > cherubi5/finneon
echo 'Развитые способности  Sap
Sipper' > cherubi5/blitzle
echo 'Живет  Cave Desert Mountain' > darumaka0
echo 'Способности
Dark Art Synchronize Trace' > gabite1/umbreon
echo 'Развитые способности
Justified' > growlithe8
echo 'Способности  Torrent Hyper Cutter Shell
Armor' > krabby6
echo 'Ходы  Ancientpower Bind Body Slam Dive Double-Edge
Earth Power Icy Wind Iron Defense Knock Off Mud-Slap Rollout Seismic
Toss Sleep Talk Snore Stealth Rock Water Pulse' > totodile5/omastar
echo 'Способности
Growl Tackle Tail Whip Headbutt Sand-Attack Odor Sleuth Mud Sport Pin
Missile Covet Bestow Flail Rest Belly Drum Fling' > totodile5/zigzagoon
chmod 321 cherubi5
chmod 752 cherubi5/roselia
chmod 537 cherubi5/sealeo
chmod 624 cherubi5/finneon
chmod 737 cherubi5/armaldo
chmod 400 cherubi5/blitzle
chmod 600 darumaka0
chmod 736 gabite1
chmod 440 gabite1/umbreon
chmod 333 gabite1/archeops
chmod 755 gabite1/piloswine
chmod 004 growlithe8
chmod 640 krabby6
chmod 576 totodile5
chmod 640 totodile5/omastar
chmod 751 totodile5/lickilicky
chmod 044 totodile5/zigzagoon
chmod 736 totodile5/axew
cd ..
chmod u+r lab0/cherubi5
chmod u+r lab0/growlithe8
chmod u+w lab0/totodile5
chmod u+r lab0/totodile5/zigzagoon
chmod u+w lab0/cherubi5/sealeo
chmod u+r lab0/darumaka0
ln -s ~/lab0/growlithe8 ~/lab0/cherubi5/blitzlegrowlithe
cp lab0/krabby6 lab0/totodile5/omastarkrabby
rm lab0/cherubi5/blitzlegrowlithe
ln ~/lab0/growlithe8 ~/lab0/cherubi5/blitzlegrowlithe
cat lab0/gabite1/umbreon lab0/cherubi5/blitzle > lab0/growlithe8_44
cp -r lab0/totodile5 lab0/totodile5/axew
ln -s ~/lab0/cherubi5 ~/lab0/Copy_3
cp lab0/darumaka0 lab0/cherubi5/sealeo
chmod u-r lab0/cherubi5
chmod u-r lab0/growlithe8
chmod u-w lab0/totodile5
chmod u-r lab0/totodile5/zigzagoon
chmod u-w lab0/cherubi5/sealeo
chmod u-r lab0/darumaka0
cd lab0
wc -l cherubi5/finneon cherubi5/blitzle gabite1/umbreon >/tmp/lines1 2>/dev/null
ls -ld ./**/a* 2>/tmp/err1 | tail -n2
grep -ni 'e$' cherubi5/finneon cherubi5/blitzle gabite1/umbreon
ls -ld ./**/a* 2>/tmp/err2  | sort -nk 2
ls -ldt ./**/a* | head -n3
ls cherubi5 2>/tmp/err3 | sort
chmod -R 777 totodile5
rm -f krabby6
rm -f gabite1/umbreon
rm -f cherubi5/blitzlegrowlit
rm -rf totodile5
rm -rf cherubi5/roselia

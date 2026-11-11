
# ARICIA Game Boost

**Shizuku-based Android System Configuration & Game Boost App (No Root Required)**

แอปปรับแต่งระบบและเร่งประสิทธิภาพเกม สำหรับ Android ทุกยี่ห้อ  
ใช้ Shizuku (ไม่ต้อง Root)  
ชื่อแอป: **ARICIA Game Boost**  
UI: สีเทา Clean, Material 3

> **สำคัญมาก**  
> Repository นี้มีเฉพาะ **Source Code + เอกสารการสร้าง**  
> **ไม่มีไฟล์ APK สำเร็จรูป** ให้ดาวน์โหลด  
> คุณต้อง Build APK เองด้วย Android Studio

## คุณสมบัติหลัก
- ขออนุญาต Shizuku + Display over other apps + Do Not Disturb
- ปรับ Refresh Rate: 60 / 90 / 120 / 144 Hz (adaptive ตามเครื่อง)
- ปรับ Resolution: 720p / 1080p / 1440p + ปุ่ม Reset
- ปรับความไว Touch & Swipe: 1x–5x
- Network Optimization (ลด ping + เร่งเน็ต)
- Performance Modes: Battery Saver / Balanced / Maximum
- Boost Modes (General / Medium / Maximum) + ล้าง RAM + RAM Plus / ZRAM
- รองรับทุกยี่ห้อทุกโมเดล (มีระบบตรวจจับเครื่อง)
- มีโปรไฟล์พิเศษสำหรับ Samsung Galaxy A15 5G

## ข้อจำกัด (อ่านก่อนใช้)
- Frame Generation แบบ 2x จริง ๆ **ทำไม่ได้** โดยไม่มี Root หรือ Module
- บางคำสั่งขึ้นอยู่กับยี่ห้อเครื่อง (โดยเฉพาะ Xiaomi, Oppo, Vivo)
- การเปลี่ยน Resolution ผิดพลาดอาจทำให้หน้าจอเพี้ยน → มีปุ่ม Reset เสมอ

## โครงสร้างโปรเจกต์
- `docs/` ← เอกสารทีละขั้นตอนทั้งหมด
- `app/` ← Source Code Android (จะเพิ่มภายหลัง)

## วิธีเริ่มต้น (สำหรับผู้ที่ต้องการ Build เอง)
1. Clone หรือ Download repository นี้
2. เปิดโปรเจกต์ด้วย **Android Studio**
3. อ่านเอกสารตามลำดับในโฟลเดอร์ `docs/`:
   - [01-overview-and-limitations.md](docs/01-overview-and-limitations.md)
   - [02-project-setup.md](docs/02-project-setup.md)
   - [03-shizuku-integration.md](docs/03-shizuku-integration.md)
   - ... ไปจนจบ

4. Build APK เองตาม [07-building-the-apk.md](docs/07-building-the-apk.md)

## ความต้องการของระบบ
- Android 8.0 ขึ้นไป (API 26+)
- ติดตั้งแอป Shizuku และเปิดใช้งาน
- ให้อนุญาต Shizuku กับแอปนี้

## คำเตือน
การใช้คำสั่งระบบผิดพลาดอาจทำให้เครื่องมีปัญหาได้ชั่วคราว  
ผู้พัฒนาไม่รับผิดชอบต่อความเสียหายใด ๆ  
ใช้ด้วยความระมัดระวังและทดสอบปุ่ม Reset ก่อนเสมอ

## License
MIT License
# 🚀 ARICIA Game Boost (Shizuku Edition)

**ARICIA Game Boost** เป็นแอปพลิเคชันสำหรับเพิ่มประสิทธิภาพ Android (Android Optimization App) ระดับระบบ ที่สร้างขึ้นด้วย Jetpack Compose แอปนี้ใช้ **Shizuku API** เพื่อรันคำสั่ง ADB shell จากตัวเครื่องได้โดยตรง **ทำให้ไม่จำเป็นต้อง Root เครื่อง** รองรับการใช้งานกับมือถือ Android ทุกแบรนด์ (Samsung, Xiaomi, Motorola, Pixel ฯลฯ)

## 🌟 ฟีเจอร์หลัก (Features)
* **No Root Required**: ปรับแต่งระบบเชิงลึกได้อย่างปลอดภัยผ่าน Shizuku
* **Modern UI**: หน้าตาแอปเรียบหรู โทนสีเทา สบายตา ใช้งานง่าย
* **Display Control**: บังคับอัตรารีเฟรชหน้าจอ (60Hz, 90Hz, 120Hz, 144Hz) และปรับลดความละเอียดจอ (720p, 1080p, 1440p) เพื่อรีดเฟรมเรต (FPS) สูงสุด
* **Touch Acceleration**: ปรับความไวของการสัมผัสหน้าจอ (1x ถึง 5x)
* **Network Optimization**: สลับไปใช้ Cloudflare DNS อัตโนมัติ เพื่อลดปิง (Ping) และลดความหน่วงในเกม
* **Performance Modes**: สลับโหมดประหยัดแบตเตอรี่, โหมดสมดุล, และโหมดประสิทธิภาพสูงสุด (ปิดการลดความเร็วของ CPU)
* **RAM & ZRAM Boost**: ปิดแอปพลิเคชันเบื้องหลังทั้งหมดและล้างแคช เพื่อคืนพื้นที่ RAM ในทันที

---

## 📁 โครงสร้างโฟลเดอร์ของโปรเจกต์ (File Structure)

หากคุณต้องการนำโค้ดไป Build ใน Android Studio โครงสร้างไฟล์ควรจะเป็นแบบนี้:
```text
ARICIA-Game-Boost/
├── app/
│   ├── build.gradle.kts
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           └── java/com/aricia/gameboost/
│               └── MainActivity.kt

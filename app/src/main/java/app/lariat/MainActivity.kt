package app.lariat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items as gridItems
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs
import kotlin.random.Random

class MainActivity: ComponentActivity(){
 override fun onCreate(savedInstanceState:Bundle?){super.onCreate(savedInstanceState);setContent{App()}}
}

val Bg=Color(0xFF080D1D); val Panel=Color(0xFF17213D); val Glass=Color(0x99212D50)
val Blue=Color(0xFF9BB8FF); val Peach=Color(0xFFFFB99A); val Mint=Color(0xFF80E0BE); val White=Color(0xFFF5F7FF)

enum class K { TTT, CONNECT, PAIRS, MINES, LIGHTS, FIFTEEN, MATH, ODD, GUESS, PRIME, NIM, SEQ }
data class G(val name:String,val cat:String,val icon:String,val sub:String,val k:K)
val allGames=listOf(
 G("Крестики-нолики","Стратегия","×○","Классика на двоих",K.TTT),
 G("Четыре в ряд","Стратегия","●","Собери линию из четырёх",K.CONNECT),
 G("Пары","Память","◈","Найди восемь пар",K.PAIRS),
 G("Сапёр","Логика","✦","5 мин на поле 5×5",K.MINES),
 G("Погаси свет","Логика","☼","Выключи все клетки",K.LIGHTS),
 G("Пятнашки","Головоломки","15","Верни числа на места",K.FIFTEEN),
 G("Быстрый счёт","Числа","+","Решай примеры",K.MATH),
 G("Найди лишнее","Внимание","◇","Заметь отличие",K.ODD),
 G("Угадай число","Числа","?","От 1 до 100",K.GUESS),
 G("Простое число","Числа","P","Да или нет?",K.PRIME),
 G("Ним","Стратегия","|||","Забери последнюю фишку",K.NIM),
 G("Числовой ряд","Числа","∞","Продолжи последовательность",K.SEQ)
)

@Composable fun App(){
 var game by remember{mutableStateOf<G?>(null)}
 MaterialTheme(colorScheme=darkColorScheme(primary=Blue,secondary=Peach,background=Bg,surface=Panel)){
  Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color(0xFF1C2B55),Bg)))){
   if(game==null) Home{game=it} else GameShell(game!!){game=null}
  }
 }
}
@Composable fun Frost(mod:Modifier=Modifier,content:@Composable ColumnScope.()->Unit){
 Column(mod.clip(RoundedCornerShape(28.dp)).background(Glass)
  .border(1.dp,Color.White.copy(.12f),RoundedCornerShape(28.dp)).padding(18.dp),content=content)
}
@Composable fun Home(open:(G)->Unit){
 var cat by remember{mutableStateOf("Все")};val cats=listOf("Все")+allGames.map{it.cat}.distinct()
 val list=if(cat=="Все")allGames else allGames.filter{it.cat==cat}
 Column(Modifier.fillMaxSize()){
  Column(Modifier.padding(20.dp)){
   Row(verticalAlignment=Alignment.CenterVertically){
    Column(Modifier.weight(1f)){Text("Lariat",fontSize=42.sp,fontWeight=FontWeight.Light,color=White);Text("Короткие игры • ясная голова",color=White.copy(.6f))}
    Box(Modifier.clip(RoundedCornerShape(14.dp)).background(Peach.copy(.16f)).padding(10.dp)){Text("v6",color=Peach,fontWeight=FontWeight.Bold)}
   }
   Spacer(Modifier.height(18.dp))
   Frost(Modifier.fillMaxWidth().clickable{open(allGames.random())}){
    Text("ИГРА ДНЯ",fontSize=12.sp,color=Blue,fontWeight=FontWeight.Bold)
    Spacer(Modifier.height(6.dp));Text("Случайный вызов",fontSize=25.sp,color=White,fontWeight=FontWeight.Bold)
    Text("Одна кнопка — новая мини-игра  →",color=White.copy(.65f))
   }
  }
  LazyRow(contentPadding=PaddingValues(horizontal=20.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)){
   items(cats){c->FilterChip(selected=cat==c,onClick={cat=c},label={Text(c)})}
  }
  BoxWithConstraints(Modifier.fillMaxSize()){
   val cols=if(maxWidth<370.dp)1 else if(maxWidth<720.dp)2 else 3
   LazyVerticalGrid(GridCells.Fixed(cols),contentPadding=PaddingValues(20.dp),horizontalArrangement=Arrangement.spacedBy(12.dp),verticalArrangement=Arrangement.spacedBy(12.dp)){
    gridItems(list,key={it.name}){g->
     Frost(Modifier.fillMaxWidth().clickable{open(g)}){
      Box(Modifier.size(44.dp).clip(CircleShape).background(Blue.copy(.13f)),contentAlignment=Alignment.Center){Text(g.icon,fontSize=20.sp,color=Peach)}
      Spacer(Modifier.height(14.dp));Text(g.name,color=White,fontWeight=FontWeight.Bold,fontSize=18.sp)
      Text(g.sub,color=White.copy(.52f),fontSize=12.sp);Spacer(Modifier.height(12.dp));Text("Играть  →",color=Blue)
     }
    }
   }
  }
 }
}
@Composable fun GameShell(g:G,back:()->Unit){
 Column(Modifier.fillMaxSize().padding(18.dp)){
  Row(verticalAlignment=Alignment.CenterVertically){
   Box(Modifier.size(48.dp).clip(CircleShape).background(Color.White.copy(.06f)).clickable{back()},contentAlignment=Alignment.Center){Text("‹",fontSize=36.sp,color=White)}
   Spacer(Modifier.width(12.dp));Column{Text(g.name,fontSize=27.sp,fontWeight=FontWeight.Bold,color=White);Text(g.cat,color=Blue)}
  }
  Spacer(Modifier.height(16.dp))
  Frost(Modifier.fillMaxWidth()){
   when(g.k){
    K.TTT->Ttt();K.CONNECT->Connect();K.PAIRS->Pairs();K.MINES->Mines();K.LIGHTS->Lights();K.FIFTEEN->Fifteen()
    K.MATH->MathGame();K.ODD->Odd();K.GUESS->Guess();K.PRIME->Prime();K.NIM->Nim();K.SEQ->Sequence()
   }
  }
 }
}
@Composable fun Cell(text:String,on:()->Unit,active:Boolean=false,mod:Modifier=Modifier){
 Box(mod.aspectRatio(1f).clip(RoundedCornerShape(16.dp)).background(if(active)Blue.copy(.23f) else Color.White.copy(.065f))
  .border(1.dp,Color.White.copy(.05f),RoundedCornerShape(16.dp)).clickable{on()},contentAlignment=Alignment.Center){
  Text(text,color=if(active)Peach else White,fontSize=25.sp,fontWeight=FontWeight.Medium)
 }
}
@Composable fun Status(title:String,sub:String){Text(title,color=White,fontSize=18.sp,fontWeight=FontWeight.Bold);Text(sub,color=White.copy(.55f),fontSize=13.sp);Spacer(Modifier.height(14.dp))}

@Composable fun Ttt(){
 var b by remember{mutableStateOf(List(9){""})};var p by remember{mutableStateOf("X")}
 val ls=listOf(listOf(0,1,2),listOf(3,4,5),listOf(6,7,8),listOf(0,3,6),listOf(1,4,7),listOf(2,5,8),listOf(0,4,8),listOf(2,4,6))
 val win=ls.firstOrNull{b[it[0]].isNotEmpty()&&b[it[0]]==b[it[1]]&&b[it[1]]==b[it[2]]}?.let{b[it[0]]}
 val draw=win==null&&b.none{it.isEmpty()}
 Status(when{win!=null->"Победил $win";draw->"Ничья";else->"Ход: $p"},"Игра вдвоём на одном телефоне")
 Column(verticalArrangement=Arrangement.spacedBy(8.dp)){repeat(3){r->Row(horizontalArrangement=Arrangement.spacedBy(8.dp)){repeat(3){c->val i=r*3+c;Cell(b[i],{if(win==null&&!draw&&b[i].isEmpty()){b=b.toMutableList().also{it[i]=p};p=if(p=="X")"O" else "X"}},b[i].isNotEmpty(),Modifier.weight(1f))}}}}
 Button({b=List(9){""};p="X"},Modifier.padding(top=14.dp)){Text("Новая партия")}
}
fun connectWinner(b:List<Int>):Int{
 for(r in 0..5)for(c in 0..6){val p=b[r*7+c];if(p==0)continue
  val dirs=listOf(0 to 1,1 to 0,1 to 1,1 to -1)
  for((dr,dc) in dirs)if((0..3).all{n->val rr=r+n*dr;val cc=c+n*dc;rr in 0..5&&cc in 0..6&&b[rr*7+cc]==p})return p
 };return 0
}
@Composable fun Connect(){
 var b by remember{mutableStateOf(List(42){0})};var p by remember{mutableIntStateOf(1)};val w=connectWinner(b)
 Status(if(w>0)"Победил ${if(w==1)"●" else "○"}" else "Ход: ${if(p==1)"●" else "○"}","Нажми любую клетку нужного столбца")
 Column(verticalArrangement=Arrangement.spacedBy(4.dp)){repeat(6){r->Row(horizontalArrangement=Arrangement.spacedBy(4.dp)){repeat(7){c->val i=r*7+c;Cell(if(b[i]==1)"●" else if(b[i]==2)"○" else "",{
  if(w==0){val rr=(5 downTo 0).firstOrNull{b[it*7+c]==0};if(rr!=null){b=b.toMutableList().also{it[rr*7+c]=p};p=3-p}}
 },b[i]!=0,Modifier.weight(1f))}}}}
 Button({b=List(42){0};p=1},Modifier.padding(top=12.dp)){Text("Заново")}
}
@Composable fun Pairs(){
 val deck=remember{(listOf("◆","●","▲","✦","♥","☀","☾","♣")*2).shuffled()}
 var matched by remember{mutableStateOf(setOf<Int>())};var first by remember{mutableIntStateOf(-1)};var second by remember{mutableIntStateOf(-1)};var moves by remember{mutableIntStateOf(0)}
 val visible=matched + listOf(first,second).filter{it>=0}
 Status(if(matched.size==16)"Все пары найдены!","Ходов: $moves • найдено ${matched.size/2}/8")
 Column(verticalArrangement=Arrangement.spacedBy(6.dp)){repeat(4){r->Row(horizontalArrangement=Arrangement.spacedBy(6.dp)){repeat(4){c->val i=r*4+c;Cell(if(i in visible)deck[i] else "?",{
  if(i !in visible){if(first<0)first=i else if(second<0){second=i;moves++;if(deck[first]==deck[second]){matched=matched+first+second;first=-1;second=-1}} else {first=i;second=-1}}
 },i in visible,Modifier.weight(1f))}}}}
 if(second>=0&&first>=0&&deck[first]!=deck[second]) Button({first=-1;second=-1},Modifier.padding(top=10.dp)){Text("Закрыть")}
}
fun neigh(i:Int):List<Int>{val r=i/5;val c=i%5;return (-1..1).flatMap{dr->(-1..1).mapNotNull{dc->val rr=r+dr;val cc=c+dc;if((dr!=0||dc!=0)&&rr in 0..4&&cc in 0..4)rr*5+cc else null}}}
@Composable fun Mines(){
 var seed by remember{mutableIntStateOf(0)};val mines=remember(seed){(0..24).shuffled().take(5).toSet()};var rev by remember(seed){mutableStateOf(setOf<Int>())};var lost by remember(seed){mutableStateOf(false)}
 val won=!lost&&rev.count{it !in mines}==20
 Status(when{lost->"Взрыв";won->"Поле очищено!";else->"Безопасных: ${rev.count{it !in mines}}/20"},"Открой клетки, избегая 5 мин")
 Column(verticalArrangement=Arrangement.spacedBy(5.dp)){repeat(5){r->Row(horizontalArrangement=Arrangement.spacedBy(5.dp)){repeat(5){c->val i=r*5+c;val n=neigh(i).count{it in mines};Cell(if(i in rev){if(i in mines)"✹" else if(n>0)"$n" else ""}else "",{if(!lost&&!won){rev=rev+i;if(i in mines)lost=true}},i in rev,Modifier.weight(1f))}}}}
 Button({seed++;rev=emptySet();lost=false},Modifier.padding(top=12.dp)){Text("Новое поле")}
}
@Composable fun Lights(){
 var seed by remember{mutableIntStateOf(0)};var b by remember(seed){mutableStateOf(List(25){Random.nextBoolean()})}
 fun tap(i:Int){val r=i/5;val c=i%5;val ids=listOf(i,i-5,i+5,i-1,i+1).filter{it in 0..24&&(it==i||abs(it/5-r)+abs(it%5-c)==1)};b=b.mapIndexed{x,v->if(x in ids)!v else v}}
 Status(if(b.none{it})"Готово!","Горит: ${b.count{it}}")
 Column(verticalArrangement=Arrangement.spacedBy(5.dp)){repeat(5){r->Row(horizontalArrangement=Arrangement.spacedBy(5.dp)){repeat(5){c->val i=r*5+c;Cell(if(b[i])"✦" else "",{tap(i)},b[i],Modifier.weight(1f))}}}}
 Button({seed++},Modifier.padding(top=12.dp)){Text("Новое поле")}
}
fun solvable(a:List<Int>):Boolean{val q=a.filter{it!=0};var inv=0;for(i in q.indices)for(j in i+1 until q.size)if(q[i]>q[j])inv++;val row=4-a.indexOf(0)/4;return (inv+row)%2==1}
fun shuffled15():List<Int>{var x:List<Int>;do{x=((1..15).toList()+0).shuffled()}while(!solvable(x)||x==(1..15).toList()+0);return x}
@Composable fun Fifteen(){
 var b by remember{mutableStateOf(shuffled15())};var moves by remember{mutableIntStateOf(0)}
 Status(if(b==(1..15).toList()+0)"Собрано!","Ходов: $moves")
 Column(verticalArrangement=Arrangement.spacedBy(5.dp)){repeat(4){r->Row(horizontalArrangement=Arrangement.spacedBy(5.dp)){repeat(4){c->val i=r*4+c;Cell(if(b[i]==0)"" else "${b[i]}",{val z=b.indexOf(0);if(abs(z/4-i/4)+abs(z%4-i%4)==1){b=b.toMutableList().also{it[z]=it[i];it[i]=0};moves++}},b[i]!=0,Modifier.weight(1f))}}}}
 Button({b=shuffled15();moves=0},Modifier.padding(top=12.dp)){Text("Перемешать")}
}
@Composable fun MathGame(){
 var a by remember{mutableIntStateOf(Random.nextInt(2,30))};var c by remember{mutableIntStateOf(Random.nextInt(2,20))};var input by remember{mutableStateOf("")};var score by remember{mutableIntStateOf(0)};var msg by remember{mutableStateOf("")}
 Status("Счёт: $score","Реши пример");Text("$a + $c = ?",fontSize=36.sp,color=White)
 OutlinedTextField(input,{input=it.filter(Char::isDigit)},label={Text("Ответ")},modifier=Modifier.fillMaxWidth())
 Button({val ok=input.toIntOrNull()==a+c;if(ok)score++;msg=if(ok)"Верно" else "Ответ: ${a+c}";a=Random.nextInt(2,30);c=Random.nextInt(2,20);input=""},Modifier.padding(top=10.dp)){Text("Проверить")}
 Text(msg,color=if(msg=="Верно")Mint else Peach,modifier=Modifier.padding(top=8.dp))
}
@Composable fun Odd(){
 var odd by remember{mutableIntStateOf(Random.nextInt(16))};var score by remember{mutableIntStateOf(0)}
 Status("Счёт: $score","Найди заполненный ромб")
 Column(verticalArrangement=Arrangement.spacedBy(6.dp)){repeat(4){r->Row(horizontalArrangement=Arrangement.spacedBy(6.dp)){repeat(4){c->val i=r*4+c;Cell(if(i==odd)"◆" else "◇",{if(i==odd){score++;odd=Random.nextInt(16)}else score=maxOf(0,score-1)},i==odd,Modifier.weight(1f))}}}}
}
@Composable fun Guess(){
 var target by remember{mutableIntStateOf(Random.nextInt(1,101))};var input by remember{mutableStateOf("")};var msg by remember{mutableStateOf("Число загадано")};var tries by remember{mutableIntStateOf(0)}
 Status("1 — 100","Угадай загаданное число")
 OutlinedTextField(input,{input=it.filter(Char::isDigit).take(3)},label={Text("Число")},modifier=Modifier.fillMaxWidth())
 Button({val n=input.toIntOrNull();if(n!=null){tries++;msg=when{n<target->"Нужно больше";n>target->"Нужно меньше";else->"Угадано за $tries!"}}},Modifier.padding(top=10.dp)){Text("Проверить")}
 Text(msg,color=Blue,modifier=Modifier.padding(top=10.dp))
 if(msg.startsWith("Угадано"))Button({target=Random.nextInt(1,101);tries=0;input="";msg="Число загадано"},Modifier.padding(top=8.dp)){Text("Ещё")}
}
fun prime(n:Int):Boolean{if(n<2)return false;var d=2;while(d*d<=n){if(n%d==0)return false;d++};return true}
@Composable fun Prime(){
 var n by remember{mutableIntStateOf(Random.nextInt(2,100))};var score by remember{mutableIntStateOf(0)}
 Status("Счёт: $score","Простое ли это число?");Text("$n",fontSize=48.sp,color=White)
 Row(horizontalArrangement=Arrangement.spacedBy(8.dp),modifier=Modifier.padding(top=10.dp)){listOf(true,false).forEach{a->Button({if(a==prime(n))score++ else score=maxOf(0,score-1);n=Random.nextInt(2,100)},Modifier.weight(1f)){Text(if(a)"Да" else "Нет")}}}
}
@Composable fun Nim(){
 var left by remember{mutableIntStateOf(15)};var p by remember{mutableIntStateOf(1)}
 Status(if(left==0)"Игрок ${3-p} победил!" else "Ход игрока $p","Осталось фишек: $left")
 Row(horizontalArrangement=Arrangement.spacedBy(8.dp)){(1..3).forEach{x->Button(enabled=left>=x&&left>0,onClick={left-=x;p=3-p},modifier=Modifier.weight(1f)){Text("−$x")}}}
 Button({left=15;p=1},Modifier.padding(top=12.dp)){Text("Заново")}
}
@Composable fun Sequence(){
 var start by remember{mutableIntStateOf(Random.nextInt(1,10))};var step by remember{mutableIntStateOf(Random.nextInt(2,7))};var input by remember{mutableStateOf("")};var score by remember{mutableIntStateOf(0)}
 val q=(0..3).map{start+it*step};Status("Счёт: $score","Продолжи ряд");Text(q.joinToString("  •  ")+"  •  ?",fontSize=22.sp,color=White)
 OutlinedTextField(input,{input=it.filter(Char::isDigit)},label={Text("Ответ")},modifier=Modifier.fillMaxWidth())
 Button({if(input.toIntOrNull()==start+4*step)score++ else score=maxOf(0,score-1);start=Random.nextInt(1,10);step=Random.nextInt(2,7);input=""},Modifier.padding(top=10.dp)){Text("Проверить")}
}

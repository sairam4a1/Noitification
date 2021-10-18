import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'mfnotif';
  form: FormGroup;
  changenotificationTypeCheck:string;
  
  submitted: boolean = false;
  isLoading: boolean = false; 
  responseMessage: string; 
  emailForm:FormGroup;
  smsForm:FormGroup;
  constructor(private formBuilder: FormBuilder, private http: HttpClient) {
    this.emailForm=this.formBuilder.group({
      toAddress: new FormControl('', [Validators.required,Validators.email]),
      subject: new FormControl(''),
      body: new FormControl('')
    });
    this.smsForm=this.formBuilder.group({
      phoneNumber: new FormControl('', [Validators.required,Validators.maxLength(13),Validators.pattern("^((\\+91-?)|0)?[0-9]{10}$")]),
      message: new FormControl('')
    });    
    this.form = this.formBuilder.group(
      {
        companyName: new FormControl('',[Validators.required,Validators.minLength(5)]),
        messageType: new FormControl(''),
        subscribeNotification: new FormControl('true'),
        notificationType: new FormControl('email'),
        emailRequest:this.emailForm,
        smsRequest:this.smsForm
      }
    );
    this.changenotificationTypeCheck='email';
  }
  ngOnInit(): void {
    this.form.removeControl('smsRequest');
    console.log(this.form.contains('smsRequest'));
  }
  changenotificationType(e:any){
    this.changenotificationTypeCheck=e.target.value
    this.form.controls.messageType.setValue('');
    if(this.changenotificationTypeCheck=='email'){

      if(!this.form.contains('emailRequest')){
        this.form.addControl('emailRequest',this.emailForm)
      }
      this.form.removeControl('smsRequest');
    }else if(this.changenotificationTypeCheck=='sms'){
      if(!this.form.contains('smsRequest')){
        this.form.addControl('smsRequest',this.smsForm)
      }
      this.form.removeControl('emailRequest');
    }else{
      if(!this.form.contains('emailRequest')){
        this.form.addControl('emailRequest',this.emailForm)
      }
      if(!this.form.contains('smsRequest')){
        this.form.addControl('smsRequest',this.smsForm)
      }
    }
    
  }
  changeMessagetype(e:any){
    
    var orderId:number=this.generateOrderNumber();
    if(this.changenotificationTypeCheck=='email'){
      this.form.controls.emailRequest.patchValue({
        subject:'Your '+this.form.controls.companyName.value+' '+this.form.controls.messageType.value+
        ' - Your order number is: '+orderId,
        body:'Your order number is: '+orderId
      });

    }
    else if(this.changenotificationTypeCheck=='sms'){
     
      this.form.controls.smsRequest.patchValue({
        message:'Your '+this.form.controls.messageType.value+
        ' - Your order number is: '+orderId
      });
    }else{
      this.form.addControl('emailRequest',this.form.controls.emailRequest); 
      this.form.addControl('smsRequest',this.form.controls.smsRequest); 
      this.form.controls.emailRequest.patchValue({
        subject:'Your '+this.form.controls.companyName.value+' '+this.form.controls.messageType.value+
                +' Successfully'+' - Your order number is: '+orderId,
        body:'Your order number is: '+orderId
      });
      this.form.controls.smsRequest.patchValue({
        message:'Your '+this.form.controls.messageType.value+
                ' Successfully - Your order number is: '+orderId
      });
    }
  }
  generateOrderNumber():number{
    return Math.floor(Math.random() * (10000 - 100 + 1)) + 100;
  }
  onSubmit() {   
    if (this.form.status == "VALID") {
      this.form.disable(); 
      this.isLoading = true;
      this.submitted = false; 
      this.http.post("http://localhost:9002//notification/api/notificationPublisher", this.form.value,{ responseType: 'text'}).subscribe(
        (response) => {

          this.responseMessage = response+' Soon it will delivery to concern party';

          this.form.enable(); 
          this.form.controls.companyName.setValue('');
          this.form.controls.messageType.setValue('');
          if(this.form.contains('emailRequest'))
                  this.form.controls.emailRequest.reset();
          if(this.form.contains('smsRequest'))
                  this.form.controls.smsRequest.reset();
          this.submitted = true; 
          this.isLoading = false;
          console.log(response);
          this.responseMessage = response+' Soon it will delivery to concern party';
          alert(this.responseMessage)
        },
        (error) => {
          this.responseMessage =error['error']+'.......';
          this.form.enable();
          this.submitted = true;
          this.isLoading = false; 
          console.log(error);
          alert(this.responseMessage)
        }
      );
    }
  }
}

using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using Microsoft.AspNet.Identity.Owin;
using Microsoft.Owin;
using Microsoft.Owin.Security;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Net.Mail;
using System.Security.Claims;
using System.Threading.Tasks;
using System.Web;
using PetBoardingApp.App_Start;
using PetBoardingApp.Models;

namespace PetBoardingApp.App_Start
{
    public static class EmailHelpers
    {
        public static SmtpClient GetSmtpClient()
        {
            SmtpClient smtpClient = new SmtpClient(EmailServiceCredentials.EmailSMTPUrl);
            smtpClient.Port = 587;
            smtpClient.EnableSsl = true;
            smtpClient.Credentials = new NetworkCredential(EmailServiceCredentials.EmailFromAddress, EmailServiceCredentials.EmailSMTPPasswordHash);

            return smtpClient;
        }

        public static MailMessage GenerateMailMessage(string destination, string subject, string body)
        {
            // Populate credentials
            EmailServiceCredentials.PopulateEmailCredentialsFromAppConfig();

            MailMessage mailMessage = new MailMessage(new MailAddress(EmailServiceCredentials.EmailFromAddress, EmailServiceCredentials.EmailFromName), new MailAddress(destination));
            mailMessage.Subject = EmailServiceCredentials.EmailAppName + " - " + subject;
            mailMessage.Body = body;
            mailMessage.IsBodyHtml = true;

            return mailMessage;
        }


    }
}
import torch
from torch.autograd import Variable
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
from torchvision import datasets, transforms
from PIL import Image
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.image as mpimg
import os
import time

# Following this tutorial: http://adventuresinmachinelearning.com/pytorch-tutorial-deep-learning/

# We will create a 4 layer fully connected Feedforward neural network

# Input layer: 28*28 = 784 neurons
# Layer 1: 200 neurons
# Layer 2: 200 neurons
# Layer 3: 10 neurons

# Helper functions
def load_image(image_path):
    image = Image.open(image_path)
    normalize_image = transforms.Compose([transforms.Grayscale(),transforms.ToTensor(),transforms.Normalize((0.1307,), (0.3081,))])
    show_image = transforms.Compose([transforms.Grayscale(),transforms.ToTensor(),transforms.Normalize((0.1307,),(0.3081,)),transforms.ToPILImage()])
    image_to_show = show_image(image)

    image_normalized = normalize_image(image)
    image_normalized = image_normalized.view(-1, 28 * 28)
    return image_normalized


class Net(nn.Module):
    def __init__(self):
        super(Net, self).__init__()
        self.fc1 = nn.Linear(28 * 28, 200)
        self.fc2 = nn.Linear(200, 200)
        self.fc3 = nn.Linear(200, 10)

    def forward(self, x):
        x = F.relu(self.fc1(x))
        x = F.relu(self.fc2(x))
        x = self.fc3(x)
        return F.log_softmax(x)


def create_nn(learning_rate =0.01, batch_size = 200, epochs = 10, log_interval = 10, saving_name = "net.pt"):
    print("Loading the training set...")
    # Training set (Loading the MNIST dataset)
    train_loader = torch.utils.data.DataLoader(
        datasets.MNIST('../data', train=True, download=True,
                       transform=transforms.Compose([transforms.ToTensor(),
                                                     transforms.Normalize((0.1307,), (0.3081,))])),
        batch_size=batch_size, shuffle=True)

    print("Loading the test set...")
    # Test set
    test_loader = torch.utils.data.DataLoader(
        datasets.MNIST('../data', train=False,
                       transform=transforms.Compose([transforms.ToTensor(),
                                                     transforms.Normalize((0.1307,), (0.3081,))])),
        batch_size=batch_size, shuffle=True)


    print("Instantiating the network...")
    # Instantiate the network and set the optimizer and the loss fuction
    net = Net()
    # create a stochastic gradient descent optimizer
    optimizer = optim.SGD(net.parameters(), lr=learning_rate, momentum=0.9)
    # create a loss function
    criterion = nn.NLLLoss()


    print("Starting the training...")
    # run the main training loop
    for epoch in range(epochs):
        for batch_idx, (data, target) in enumerate(train_loader):
            data, target = Variable(data), Variable(target)
            # resize data from (batch_size, 1, 28, 28) to (batch_size, 28*28)
            data = data.view(-1, 28*28)
            optimizer.zero_grad()
            net_out = net(data)
            loss = criterion(net_out, target)
            loss.backward()
            optimizer.step()
            if batch_idx % log_interval == 0:
                print('Train Epoch: {} [{}/{} ({:.0f}%)]\tLoss: {:.6f}'.format(
                    epoch, batch_idx * len(data), len(train_loader.dataset),
                           100. * batch_idx / len(train_loader), loss.data[0]))

    print("Starting the testing...")
    # run a test loop
    test_loss = 0
    correct = 0
    for data, target in test_loader:
        data, target = Variable(data, volatile=True), Variable(target)
        data = data.view(-1, 28 * 28)
        net_out = net(data)
        # sum up batch loss
        test_loss += criterion(net_out, target).data[0]
        pred = net_out.data.max(1)[1]  # get the index of the max log-probability
        correct += pred.eq(target.data).sum()

    test_loss /= len(test_loader.dataset)
    print('\nTest set: Average loss: {:.4f}, Accuracy: {}/{} ({:.0f}%)\n'.format(
        test_loss, correct, len(test_loader.dataset),
    100. * correct / len(test_loader.dataset)))

    torch.save(net.state_dict(), saving_name)


def guess_number(image, loading_name):
    f = open(loading_name, "rb")
    net = Net()
    net.load_state_dict(torch.load(loading_name))
    net.eval()
    net_out = net(image)
    prediction = net_out.data.max(1)[1]
    return prediction.data.numpy()[0]


def start(prompt):
    print("WAKING UP!")
    print("WHAT SHOULD I DO?")
    print("CREATE NN (nn) OR GUESS NUMBER (gn)?")
    task = input(prompt)
    if task == "nn":
        print("CREATING FFWD NEURAL NETWORK...")
        create_nn()
    elif task == "gn":
        print("GIVE ME THE NUMBER!")
        previous_answer = -1
        while 1:
            image = load_image("test.png")
            current_answer = guess_number(image, "net.pt")
            if current_answer != previous_answer:
                print("I THINK THE NUMBER IS " + str(current_answer))
                print("DRAW ME ANOTHER!")
                previous_answer = current_answer
            time.sleep(2)


start("> ")

